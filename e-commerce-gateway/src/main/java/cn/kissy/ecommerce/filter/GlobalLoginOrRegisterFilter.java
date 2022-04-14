package cn.kissy.ecommerce.filter;

import cn.hutool.json.JSONUtil;
import cn.kissy.ecommerce.constant.CommonConstant;
import cn.kissy.ecommerce.constant.GatewayConstant;
import cn.kissy.ecommerce.util.TokenParseUtil;
import cn.kissy.ecommerce.vo.JwtToken;
import cn.kissy.ecommerce.vo.LoginUserInfo;
import cn.kissy.ecommerce.vo.UsernameAndPassword;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 全局登录鉴权过滤器
 * @ClassName GlobalLoginOrRegisterFilter
 * @Author kingdee
 * @Date 2022/4/14
 * @Version 1.0
 **/
@Slf4j
@Component
public class GlobalLoginOrRegisterFilter implements GlobalFilter, Ordered {

    /**
     * 注册中心客户端，可以从注册中心中获取实例信息
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 登录，注册，鉴权
     * 1. 如果是登录或注册，则去授权中心拿到Token并返回给客户端
     * 2. 如果是访问其他的服务，则鉴权，没有权限返回401
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 1. 获取到 request 和 response
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 2. 如果是登录
        if (request.getURI().getPath().contains(GatewayConstant.LOGIN_URI)){
            // 去授权中心拿token
            String token = getTokenFromAuthorityCenter(request, GatewayConstant.AUTHORITY_CENTER_TOKEN_URL_FORMAT);

            // 往header里设置，不能设置null
            response.getHeaders().add(CommonConstant.JWT_USER_INFO_KEY, null == token ? "null":token);

            // 返回
            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        }

        // 3. 如果是注册
        if (request.getURI().getPath().contains(GatewayConstant.REGISTER_URI)){
            // 去授权中心拿token：先创建用户，再返回token
            String token = getTokenFromAuthorityCenter(request, GatewayConstant.AUTHORITY_CENTER_REGISTER_URL_FORMAT);

            // header里不能为null
            response.getHeaders().add(CommonConstant.JWT_USER_INFO_KEY, null == token ? "null":token);

            // 返回
            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        }

        // 4. 访问其他的服务，则鉴权，校验是否能够从token中解析出用户信息
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(CommonConstant.JWT_USER_INFO_KEY);
        LoginUserInfo loginUserInfo = null;

        try{
            loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        }catch (Exception ex){
            log.error("parse user info from token error: [{}]", ex.getMessage(), ex);
        }

        // 获取不到登录用户信息，返回401
        if (null == loginUserInfo){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        // 解析通过则放行
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        // 因为必须从缓存中获取数据，所以优先级需低过cache的class
        return HIGHEST_PRECEDENCE + 2;
    }

    /**
     * 从授权中心获取Token
     * @param request
     * @param uriFormat
     * @return
     */
    private String getTokenFromAuthorityCenter(ServerHttpRequest request, String uriFormat){
        // service id 就是服务名字，实际上实现的就是负载均衡
        ServiceInstance serviceInstance = loadBalancerClient.choose(CommonConstant.AUTHORITY_CENTER_SERVICE_ID);

        log.info("Nacos Client Info: [{}], [{}], [{}]", serviceInstance.getInstanceId(), serviceInstance.getServiceId(),
                JSONUtil.toJsonStr(serviceInstance.getMetadata()));

        // 拼接URL
        String requestUrl = String.format(uriFormat, serviceInstance.getHost(), serviceInstance.getPort());
        // 反序列化解析成 body
        UsernameAndPassword requestBody = JSON.parseObject(parseBodyFromRequest(request), UsernameAndPassword.class);
        log.info("login request url and body: [{}], [{}]", requestUrl, JSON.toJSONString(requestBody));

        // 像授权中心发送请求
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JwtToken token = restTemplate.postForObject(
                requestUrl,
                new HttpEntity<>(JSON.toJSONString(requestBody), headers),
                JwtToken.class
        );
        if (null != token){
            return token.getToken();
        }
        return null;
    }


    /**
     * 从 Post请求中获取到请求数据 -- 指的是从 cache 中缓存的数据
     * @param request
     * @return
     */
    private String parseBodyFromRequest(ServerHttpRequest request){
        // 获取请求体
        Flux<DataBuffer> body = request.getBody();
        // 原子引用
        AtomicReference<String> bodyRef = new AtomicReference<>();

        // 订阅缓冲区去消费请求体中的数据
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());

            // 一定要把buffer释放掉，因为GlobalCacheRequestBodyFilter为了确保缓冲区不被释放，所以一直保存着！
            // 如若不释放，会出现内存泄漏
            DataBufferUtils.release(buffer);
            // set到原子引用中
            bodyRef.set(charBuffer.toString());
        });
        // 获取 request body
        return bodyRef.get();
    }
}
