package cn.kissy.ecommerce.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

/**
 * 用户账户余额信息
 * @ClassName BalanceInfo
 * @Author kingdee
 * @Date 2022/4/20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户账户余额信息")
public class BalanceInfo {

    @ApiModelProperty(value = "用户主键 id")
    private Long userId;

    @ApiModelProperty(value = "用户账户余额")
    private Long balance;
}
