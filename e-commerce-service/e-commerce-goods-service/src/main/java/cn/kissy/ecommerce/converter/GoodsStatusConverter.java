package cn.kissy.ecommerce.converter;

import cn.kissy.ecommerce.constant.GoodsStatus;

import javax.persistence.AttributeConverter;

/**
 * 商品状态枚举属性转换器
 * @ClassName GoodsStatusConverter
 * @Author kingdee
 * @Date 2022/4/22
 **/
public class GoodsStatusConverter implements AttributeConverter<GoodsStatus, Integer> {

    /**
     * 转换成可以存入数据表的基本类型
     * @param goodsStatus
     * @return
     */
    @Override
    public Integer convertToDatabaseColumn(GoodsStatus goodsStatus) {
        return goodsStatus.getStatus();
    }

    /**
     * 还原数据表中的字段值到Java数据类型
     * @param status
     * @return
     */
    @Override
    public GoodsStatus convertToEntityAttribute(Integer status) {
        return GoodsStatus.of(status);
    }
}
