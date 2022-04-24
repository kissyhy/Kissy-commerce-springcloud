package cn.kissy.ecommerce.service.async;

import cn.kissy.ecommerce.goods.GoodsInfo;

import java.util.List;

/**
 * 异步服务接口定义
 * @ClassName IAsyncService
 * @Author kingdee
 * @Date 2022/4/24
 **/
public interface IAsyncService {

    /**
     * 异步将商品信息保存下来
     * @param goodsInfos
     * @param taskId
     */
    void asyncImportGoods(List<GoodsInfo> goodsInfos, String taskId);
}
