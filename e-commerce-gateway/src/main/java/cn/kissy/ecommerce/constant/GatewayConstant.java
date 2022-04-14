package cn.kissy.ecommerce.constant;

/**
 * 网关常量定义
 * @ClassName GatewayConstant
 * @Author kingdee
 * @Date 2022/4/14
 * @Version 1.0
 **/
public class GatewayConstant {

    /**
     * 登录的 uri
     */
    public static final String LOGIN_URI = "/e-commerce/login";

    /**
     * 注册的 uri
     */
    public static final String REGISTER_URI = "/e-commerce/register";

    /**
     * 去授权中心拿到登录 token 的 uri 格式化接口
     */
    public static final String AUTHORITY_CENTER_TOKEN_URL_FORMAT = "http://%s:%s/ecommerce-authority-center/authority/token";

    /**
     * 去授权中心注册并拿到 token 的 uri 格式化接口
     */
    public static final String AUTHORITY_CENTER_REGISTER_URL_FORMAT = "http://%s:%s/ecommerce-authority-center/authority/register";
}
