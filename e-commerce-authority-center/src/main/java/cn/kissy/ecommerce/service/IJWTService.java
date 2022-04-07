package cn.kissy.ecommerce.service;

import cn.kissy.ecommerce.vo.UsernameAndPassword;

/**
 * JWT 相关服务接口定义
 * @ClassName IJWTService
 * @Author kingdee
 * @Date 2022/4/7
 * @Version 1.0
 **/
public interface IJWTService {

    /**
     * 生成JWT Token，使用默认的超时时间
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    String generateToken(String username, String password) throws Exception;

    /**
     * 生成指定超时时间的Token，单位是天
     * @param username
     * @param password
     * @param expire
     * @return
     * @throws Exception
     */
    String generateToken(String username, String password, int expire) throws Exception;

    /**
     * 注册用户并生成Token返回
     * @param usernameAndPassword
     * @return
     * @throws Exception
     */
    String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception;
}
