package cn.kissy.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录用户信息(已登录的用户信息)
 * @ClassName LoginUserInfo
 * @Author kingdee
 * @Date 2022/4/7
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserInfo {

    /*
      这里的用户信息是从Token里解析出来的 id和username
     */

    /**
     * 用户表的主键id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

}
