package cn.kissy.ecommerce.dao;

import cn.kissy.ecommerce.constant.BrandCategory;
import cn.kissy.ecommerce.constant.GoodsCategory;
import cn.kissy.ecommerce.entity.EcommerceGoods;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;


/**
 * EcommerceGoods Dao接口定义
 * @ClassName EcommerceGoodsDao
 * @Author kingdee
 * @Date 2022/4/24
 **/
public interface EcommerceGoodsDao extends PagingAndSortingRepository<EcommerceGoods, Long> {

    /**
     * 根据查询条件查询商品表，并限制返回结果
     * select * from t_commerce_goods where goods_category = ? and brand_category = ? and goods_name = ? limit 1;
     * @return
     */
    Optional<EcommerceGoods> findFirst1ByGoodsCategoryAndBrandCategoryAndGoodsName(
            GoodsCategory goodsCategory, BrandCategory brandCategory, String goodsName
    );
}
