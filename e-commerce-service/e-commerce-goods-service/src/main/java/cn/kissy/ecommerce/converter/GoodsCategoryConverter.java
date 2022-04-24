package cn.kissy.ecommerce.converter;

import cn.kissy.ecommerce.constant.GoodsCategory;

import javax.persistence.AttributeConverter;

/**
 * 商品类别枚举属性转换器
 * @ClassName GoodsCategoryConverter
 * @Author kingdee
 * @Date 2022/4/22
 **/
public class GoodsCategoryConverter implements AttributeConverter<GoodsCategory, String> {

    /**
     * 转换成可以存入数据表的基本类型
     * @param goodsCategory
     * @return
     */
    @Override
    public String convertToDatabaseColumn(GoodsCategory goodsCategory) {
        return goodsCategory.getCode();
    }

    /**
     * 还原数据表中的字段值到Java数据类型
     * @param code K-V的键
     * @return
     */
    @Override
    public GoodsCategory convertToEntityAttribute(String code) {
        return GoodsCategory.of(code);
    }
}
