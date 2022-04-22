package cn.kissy.ecommerce.converter;

import cn.kissy.ecommerce.constant.BrandCategory;

import javax.persistence.AttributeConverter;

/**
 * 品牌分类枚举属性转换器
 * @ClassName BrandCategoryConverter
 * @Author kingdee
 * @Date 2022/4/22
 **/
public class BrandCategoryConverter implements AttributeConverter<BrandCategory, String> {
    @Override
    public String convertToDatabaseColumn(BrandCategory brandCategory) {
        return brandCategory.getCode();
    }

    @Override
    public BrandCategory convertToEntityAttribute(String code) {
        return BrandCategory.of(code);
    }
}
