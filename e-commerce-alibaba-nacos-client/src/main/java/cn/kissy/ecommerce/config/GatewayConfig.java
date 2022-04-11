package cn.kissy.ecommerce.config;

import org.springframework.context.annotation.Configuration;

/**
 * 配置类，读取Nacos相关的配置项，用于配置监听器
 * @ClassName GatewayConfig
 * @Author kingdee
 * @Date 2022/4/11
 * @Version 1.0
 **/
@Configuration
public class GatewayConfig {

    /**
     * 读取配置的超时时间
     */
    public static final long DEFAULT_TIMEOUT = 30000;
}
