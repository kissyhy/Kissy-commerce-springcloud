package cn.kissy.ecommerce.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 主键 ids
 * @ClassName TableId
 * @Author kingdee
 * @Date 2022/4/20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "通用 id 对象")
public class TableId {

    @ApiModelProperty(value = "数据表主键")
    private List<Id> ids;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "数据表记录主键对象")
    public static class Id {
        @ApiModelProperty(value = "数据表记录主键")
        private Long id;
    }
}
