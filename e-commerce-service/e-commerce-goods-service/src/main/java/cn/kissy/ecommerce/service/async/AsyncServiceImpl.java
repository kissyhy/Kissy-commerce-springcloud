package cn.kissy.ecommerce.service.async;

import cn.kissy.ecommerce.constant.GoodsConstant;
import cn.kissy.ecommerce.dao.EcommerceGoodsDao;
import cn.kissy.ecommerce.entity.EcommerceGoods;
import cn.kissy.ecommerce.goods.GoodsInfo;
import cn.kissy.ecommerce.goods.SimpleGoodsInfo;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 异步服务接口实现
 * @ClassName AsyncServiceImpl
 * @Author kingdee
 * @Date 2022/4/24
 **/
@Slf4j
@Service
@Transactional
public class AsyncServiceImpl implements IAsyncService{

    @Autowired
    private EcommerceGoodsDao ecommerceGoodsDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 异步任务需要加上注解，并指定使用的线程池方法
     * 异步任务处理两件事：
     * 1. 将商品信息保存到数据表
     * 2. 更新商品缓存
     * @param goodsInfos
     * @param taskId
     */
    @Async("getAsyncExecutor")
    @Override
    public void asyncImportGoods(List<GoodsInfo> goodsInfos, String taskId) {
        log.info("async task running taskId: [{}]", taskId);

        StopWatch watch = StopWatch.createStarted();

        // 1. 如果是GoodsInfo中存在重复的商品，不保存；直接返回，记录错误日志
        // 请求数据是否合法的标记, false代表合法
        boolean isIllegal = false;

        // 将商品信息字段 join 在一起，用来判断是否存在重复
        Set<String> goodsJointInfos = new HashSet<>(goodsInfos.size());

        // 过滤出来的，可以入库的商品信息（规则按照自己的业务需要自定义即可）
        List<GoodsInfo> filteredGoodsInfo = new ArrayList<>(goodsInfos.size());

        // 走一遍循环，过滤非法参数与判定当前请求是否合法
        for (GoodsInfo goods : goodsInfos) {
            // 基本条件不满足的，直接过滤掉
            if (goods.getPrice() <= 0 || goods.getSupply() <= 0){
                log.info("goods info is invalid: [{}]", JSON.toJSONString(goods));
                continue;
            }

            // 组合商品信息, 因为在建表时，设置了这三个参数的唯一索引。重复的话就是不符合业务的一个商品
            String jointInfo = String.format("%s,%s,%s",
                    goods.getGoodsCategory(), goods.getBrandCategory(), goods.getGoodsName());
            if (goodsJointInfos.contains(jointInfo)){
                isIllegal = true;
            }

            // 加入到两个容器中
            goodsJointInfos.add(jointInfo);
            filteredGoodsInfo.add(goods);
        }

        // 如果存在重复商品或者是没有需要入库的商品，直接打印日志返回
        if (isIllegal || CollectionUtils.isEmpty(filteredGoodsInfo)){
            watch.stop();
            log.warn("import nothing: [{}]", JSON.toJSONString(filteredGoodsInfo));
            log.info("check and import goods done: [{}]ms", watch.getTime(TimeUnit.MILLISECONDS));
            return;
        }

        // 把goods转换成实体表对象
        List<EcommerceGoods> ecommerceGoods = filteredGoodsInfo.stream()
                .map(EcommerceGoods :: to)
                .collect(Collectors.toList());

        List<EcommerceGoods> targetGoods = new ArrayList<>(ecommerceGoods.size());

        // 2. 保存GoodsInfo之前先判断下是否存在重复商品
        ecommerceGoods.forEach(g -> {
            // limit 1, 如果存在重复的商品就直接过滤掉，用orElse
            if (null != ecommerceGoodsDao.findFirst1ByGoodsCategoryAndBrandCategoryAndGoodsName(
                g.getGoodsCategory(), g.getBrandCategory(), g.getGoodsName()
            ).orElse(null)){
                return;
            }

            targetGoods.add(g);
        });

        // 3.商品信息入库
        List<EcommerceGoods> savedGoods = IterableUtils.toList(ecommerceGoodsDao.saveAll(targetGoods));

        // 4.将入库商品信息同步到 Redis 中
        saveNewGoodsInfoToRedis(savedGoods);

        log.info("save goods into to db and redis: [{}]", savedGoods.size());
        watch.stop();
        log.info("check and import goods success: [{}]ms", watch.getTime(TimeUnit.MILLISECONDS));
    }

    /**
     * 将保存到数据表中的数据缓存到Redis中
     * dict: key -> <id, SimpleGoodsInfo(json)>
     * @param savedGoods
     */
    private void saveNewGoodsInfoToRedis(List<EcommerceGoods> savedGoods){

        // 由于 Redis 是内存存储，只存储简单商品信息
        List<SimpleGoodsInfo> simpleGoodsInfos = savedGoods.stream()
                .map(EcommerceGoods::toSimple)
                .collect(Collectors.toList());

        Map<String, String> id2JsonObject = new HashMap<>(simpleGoodsInfos.size());
        simpleGoodsInfos.forEach(g -> id2JsonObject.put(g.getId().toString(), JSON.toJSONString(g)));

        // 保存到Redis中，可能是不可靠的，并不是强依赖的
        redisTemplate.opsForHash().putAll(GoodsConstant.ECOMMERCE_GOODS_DICT_KEY, id2JsonObject);
    }
}
