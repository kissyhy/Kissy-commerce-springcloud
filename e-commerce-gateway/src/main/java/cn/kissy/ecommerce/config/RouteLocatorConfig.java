package cn.kissy.ecommerce.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置登录请求转发规则
 * @ClassName RouteLocatorConfig
 * @Author kingdee
 * @Date 2022/4/14
 * @Version 1.0
 **/
@Configuration
public class RouteLocatorConfig {

    /**
     * 使用代码定义路由规则，在网关层面拦截下登录和注册接口
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator loginRouteLocator(RouteLocatorBuilder builder){
        // 手工定义 gateway 路由规则需要指定 id、path 和 uri
        return builder.routes().route(
                "e_commerce_authority",
                r -> r.path(
                        "/imooc/e-commerce/login",
                        "/imooc/e-commerce/register").uri("http://localhost:9001/")
                ).build();
    }
}
