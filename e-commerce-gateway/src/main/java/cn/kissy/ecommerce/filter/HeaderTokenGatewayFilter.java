package cn.kissy.ecommerce.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * HTTP 头部携带 Token 验证过滤器
 * @ClassName HeaderTokenGatewayFilter
 * @Author kingdee
 * @Date 2022/4/14
 * @Version 1.0
 **/
public class HeaderTokenGatewayFilter implements GatewayFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 从 http Header 中寻找 key为token，value为imooc的键值对
        String name = exchange.getRequest().getHeaders().getFirst("token");
        if ("imooc".equals(name)){
            // 继续往下走
            return chain.filter(exchange);
        }

        // 标记此次请求没有权限，并结束这次请求
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        // 认为这次请求就结束了
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        // HIGHEST_PRECEDENCE = 最高权限级
        return HIGHEST_PRECEDENCE + 2;
    }
}
