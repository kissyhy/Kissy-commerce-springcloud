package cn.kissy.ecommerce.service;

import cn.kissy.ecommerce.common.TableId;
import cn.kissy.ecommerce.goods.DeductGoodsInventory;
import cn.kissy.ecommerce.goods.GoodsInfo;
import cn.kissy.ecommerce.goods.SimpleGoodsInfo;
import cn.kissy.ecommerce.vo.PageSimpleGoodsInfo;

import java.util.List;

/**
 * 商品微服务相关服务接口定义
 * @ClassName IGoddsService
 * @Author kingdee
 * @Date 2022/4/24
 **/
public interface IGoodsService {

    /**
     * 根据TableId 查询商品详细信息
     * @param tableId
     * @return
     */
    List<GoodsInfo> getGoodsInfoByTableId(TableId tableId);

    /**
     * 获取分页的商品信息
     * @param page 页数
     * @return PageSimpleGoodsInfo
     */
    PageSimpleGoodsInfo getSimpleGoodsInfoByPage(int page);

    /**
     * 根据 TableId 查询简单商品信息
     * @param tableId
     * @return
     */
    List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(TableId tableId);

    /**
     * 扣减商品库存
     * @param deductGoodsInventories
     * @return
     */
    Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories);
}
