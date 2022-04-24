package cn.kissy.ecommerce.vo;

import cn.kissy.ecommerce.goods.SimpleGoodsInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页的商品信息对象
 * @ClassName PageSimpleGoodsInfo
 * @Author kingdee
 * @Date 2022/4/24
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "分页商品信息对象")
public class PageSimpleGoodsInfo {

    @ApiModelProperty(value = "分页简单商品信息")
    private List<SimpleGoodsInfo> simpleGoodsInfos;

    @ApiModelProperty(value = "是否有更多的商品(分页)")
    private Boolean hasMore;
}
