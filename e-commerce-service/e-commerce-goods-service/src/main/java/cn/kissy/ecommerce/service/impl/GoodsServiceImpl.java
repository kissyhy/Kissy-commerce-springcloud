package cn.kissy.ecommerce.service.impl;

import cn.kissy.ecommerce.common.TableId;
import cn.kissy.ecommerce.constant.GoodsConstant;
import cn.kissy.ecommerce.dao.EcommerceGoodsDao;
import cn.kissy.ecommerce.entity.EcommerceGoods;
import cn.kissy.ecommerce.goods.DeductGoodsInventory;
import cn.kissy.ecommerce.goods.GoodsInfo;
import cn.kissy.ecommerce.goods.SimpleGoodsInfo;
import cn.kissy.ecommerce.service.IGoodsService;
import cn.kissy.ecommerce.vo.PageSimpleGoodsInfo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 商品微服务相关服务实现
 * @ClassName GoodsServiceImpl
 * @Author kingdee
 * @Date 2022/4/25
 **/
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private EcommerceGoodsDao goodsDao;

    /**
     * 根据TableId 查询商品详细信息
     * @param tableId
     * @return
     */
    @Override
    public List<GoodsInfo> getGoodsInfoByTableId(TableId tableId) {

        // 详细的商品信息，不弄从 Redis cache 中去拿
        List<Long> ids = tableId.getIds().stream()
                .map(TableId.Id::getId)
                .collect(Collectors.toList());
        log.info("get goods info by ids: [{}]", JSON.toJSONString(ids));
        List<EcommerceGoods> ecommerceGoods = IterableUtils.toList(goodsDao.findAllById(ids));

        // EcommerceGoods --> GoodsInfo
        return ecommerceGoods.stream()
                .map(EcommerceGoods::toGoodsInfo)
                .collect(Collectors.toList());
    }

    /**
     * 获取分页的商品信息
     * @param page 页数
     * @return
     */
    @Override
    public PageSimpleGoodsInfo getSimpleGoodsInfoByPage(int page) {

        // 分页不能从Redis cache中去拿
        if (page <= 1){
            // 默认是第一页
            page = 1;
        }

        // 分页的规则（可以自由修改）：1页10条数据，按照商品 id 倒序排列
        Pageable pageable = PageRequest.of(
                page - 1, 10,
                Sort.by("id").descending()
        );
        Page<EcommerceGoods> orderPage = goodsDao.findAll(pageable);

        // 是否还有更多页: 总页数是否大于当前给定的页
        boolean hasMore = orderPage.getTotalPages() > page;

        return new PageSimpleGoodsInfo(
                orderPage.getContent().stream()
                        .map(EcommerceGoods::toSimple)
                        .collect(Collectors.toList()),
                hasMore
        );
    }

    /**
     * 根据 TableId 查询简单商品信息
     * @param tableId
     * @return
     */
    @Override
    public List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(TableId tableId) {

        // 获取商品的简单信息，可以从Redis cache中去拿，拿不到需要从DB中获取并保存到Redis里面
        // Redis中的 K-V 都是字符串类型
        List<Object> goodsIds = tableId.getIds().stream()
                .map(i -> i.getId().toString())
                .collect(Collectors.toList());
        List<Object> cachedSimpleGoodsInfo = redisTemplate.opsForHash()
                .multiGet(GoodsConstant.ECOMMERCE_GOODS_DICT_KEY, goodsIds);

        // 如果从Redis中查到了商品信息，分两种情况去操作
        if (CollectionUtils.isNotEmpty(cachedSimpleGoodsInfo)){
            // 1. 如果从缓存中查询出所有需要的SimpleGoodsInfo
            if (cachedSimpleGoodsInfo.size() == goodsIds.size()){
                log.info("get simple goods info by ids(from cache): [{}]", JSON.toJSONString(goodsIds));
                return parseCachedGoodsInfo(cachedSimpleGoodsInfo);
            }
            else {
                // 2. 一半从数据表中获取（right），一半从redis cache中获取（left）
                List<SimpleGoodsInfo> left = parseCachedGoodsInfo(cachedSimpleGoodsInfo);
                // 取差集：传递进来的参数 - 缓存中查到的 = 缓存中没有的
                // CollectionUtils.subtract 就是取两个差集
                Collection<Long> subtractIds = CollectionUtils.subtract(
                        goodsIds.stream()
                                .map(g -> Long.valueOf(g.toString()))
                                .collect(Collectors.toList()),
                        left.stream()
                                .map(SimpleGoodsInfo::getId)
                                .collect(Collectors.toList())
                );
                // 缓存中没有的，查询数据表并缓存
                List<SimpleGoodsInfo> right = queryGoodsFromDBAndCacheToRedis(new TableId(
                        subtractIds.stream()
                                .map(TableId.Id::new)
                                .collect(Collectors.toList())
                ));

                // 合并 right和left
                log.info("get simple goods info by ids(from db and cache): [{}]", JSON.toJSONString(goodsIds));
                return new ArrayList<>(CollectionUtils.union(left, right));
            }
        } else {
            // 从Redis里面什么都没有查到
            return queryGoodsFromDBAndCacheToRedis(tableId);
        }
    }

    /**
     * 将缓存中的数据，反序列化成 Java Pojo对象
     * @param cachedSimpleGoodsInfo
     * @return
     */
    private List<SimpleGoodsInfo> parseCachedGoodsInfo(List<Object> cachedSimpleGoodsInfo){
        return cachedSimpleGoodsInfo.stream()
                .map(s -> JSON.parseObject(s.toString(), SimpleGoodsInfo.class))
                .collect(Collectors.toList());
    }

    /**
     * 从数据表中查询数据，并缓存到Redis中
     * @param tableId
     * @return
     */
    private List<SimpleGoodsInfo> queryGoodsFromDBAndCacheToRedis(TableId tableId){

        // 从数据表中查询数据并做转换
        List<Long> ids = tableId.getIds().stream()
                .map(TableId.Id::getId).collect(Collectors.toList());
        log.info("get simple goods info by ids(from db): [{}]", JSON.toJSONString(ids));

        List<EcommerceGoods> ecommerceGoods = IterableUtils.toList(goodsDao.findAllById(ids));
        List<SimpleGoodsInfo> result = ecommerceGoods.stream()
                .map(EcommerceGoods::toSimple)
                .collect(Collectors.toList());

        // 将结果缓存，下一次可以直接从Redis cache中查询
        log.info("cache goods info: [{}]", JSON.toJSONString(ids));
        Map<String, String> idToJsonObject = new HashMap<>(result.size());
        result.forEach(g -> idToJsonObject.put(g.getId().toString(), JSON.toJSONString(g)));

        // 保存到Redis中
        redisTemplate.opsForHash().putAll(GoodsConstant.ECOMMERCE_GOODS_DICT_KEY, idToJsonObject);
        return result;
    }

    /**
     * 扣减商品库存
     * @param deductGoodsInventories
     * @return
     */
    @Override
    public Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories) {

        // 检验下参数是否合法
        deductGoodsInventories.forEach(d -> {
            if (d.getCount() <= 0){
                throw new RuntimeException("purchase goods count need > 0");
            }
        });

        // 查询出来全部的商品
        List<EcommerceGoods> ecommerceGoods = IterableUtils.toList(
                goodsDao.findAllById(
                        deductGoodsInventories.stream()
                                .map(DeductGoodsInventory::getGoodsId)
                                .collect(Collectors.toList())
                )
        );

        // 根据传递的goodsIds查询不到商品对象，抛异常
        if (CollectionUtils.isEmpty(ecommerceGoods)){
            throw new RuntimeException("can not found any goods by request");
        }
        // 查询出来的商品数量与传递的不一样，抛异常
        if (ecommerceGoods.size() != deductGoodsInventories.size()){
            throw new RuntimeException("request is not valid");
        }
        // goodsId -> DeductGoodsInventory 的映射
        Map<Long, DeductGoodsInventory> goodsIdToInventory = deductGoodsInventories.stream()
                .collect(Collectors.toMap(DeductGoodsInventory::getGoodsId, Function.identity()));

        // 检查是否可以扣减库存，之后再去扣减库存
        ecommerceGoods.forEach(g -> {
            Long currentInventory = g.getInventory();
            Integer needDeductInventory = goodsIdToInventory.get(g.getId()).getCount();
            if (currentInventory < needDeductInventory){
                log.error("goods inventory is not enough: [{}], [{}]", currentInventory, needDeductInventory);
                throw new RuntimeException("goods inventory is not enough: " + g.getId());
            }
            // 扣减库存
            g.setInventory(currentInventory - needDeductInventory);
            log.info("deduct goods inventory: [{}], [{}], [{}]", g.getId(), currentInventory, g.getInventory());
        });

        goodsDao.saveAll(ecommerceGoods);
        log.info("deduct goods inventory done");
        return true;
    }
}
