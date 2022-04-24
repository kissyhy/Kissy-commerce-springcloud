package cn.kissy.ecommerce.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 扣减商品库存
 * @ClassName DeductGoodsInventory
 * @Author kingdee
 * @Date 2022/4/24
 **/
@ApiModel(description = "扣减商品库存对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductGoodsInventory {
    @ApiModelProperty(value = "商品表的主键id")
    private Long goodsId;

    @ApiModelProperty(value = "扣减个数")
    private Integer count;
}
