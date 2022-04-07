package cn.kissy.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户名和密码的对象
 * @ClassName UsernameAndPassword
 * @Author kingdee
 * @Date 2022/4/7
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsernameAndPassword {
    private String username;
    private String password;
}
