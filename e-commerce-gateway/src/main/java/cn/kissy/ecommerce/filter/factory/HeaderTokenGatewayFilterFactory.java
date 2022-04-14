package cn.kissy.ecommerce.filter.factory;

import cn.kissy.ecommerce.filter.HeaderTokenGatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * 过滤器工厂，所有的过滤器都会先注册到这里进行分发
 * @ClassName HeaderTokenAbstractGatewayFilterFactory
 * @Author kingdee
 * @Date 2022/4/14
 * @Version 1.0
 **/
@Component
public class HeaderTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    @Override
    public GatewayFilter apply(Object config) {
        return new HeaderTokenGatewayFilter();
    }
}
