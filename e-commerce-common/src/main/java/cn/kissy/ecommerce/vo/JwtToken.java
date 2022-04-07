package cn.kissy.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 授权中心鉴权之后给客户端的Token
 * @ClassName JwtToken
 * @Author kingdee
 * @Date 2022/4/7
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {
    /**
     * JWT字符串信息
     */
    private String token;
}
