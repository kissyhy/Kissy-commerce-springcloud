package cn.kissy.ecommerce.dao;

import cn.kissy.ecommerce.entity.EcommerceUser;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * EcommerceUserDao 接口定义
 * JpaRepository<EcommerceUser, Long> 第一个是表，第二个是主键的类型
 * @ClassName EcommerceUserDao
 * @Author kingdee
 * @Date 2022/4/7
 * @Version 1.0
 **/
public interface EcommerceUserDao extends JpaRepository<EcommerceUser, Long> {

    /**
     * 根据用户名查询 EcommerceUser 对象
     * select * from t_ecommerce_user where username = ?
     * @param username
     * @return
     */
    EcommerceUser findByUsername(String username);

    /**
     * 根据用户名和密码查询实体对象
     * select * from t_ecommerce_user where username = ? and password = ?
     * @param username
     * @param password
     * @return
     */
    EcommerceUser findByUsernameAndPassword(String username, String password);
}
