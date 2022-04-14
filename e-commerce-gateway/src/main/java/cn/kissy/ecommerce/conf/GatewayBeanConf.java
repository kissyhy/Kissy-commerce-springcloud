package cn.kissy.ecommerce.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 网关需要注入到容器中
 * @ClassName GatewayBeanConf
 * @Author kingdee
 * @Date 2022/4/14
 * @Version 1.0
 **/
@Configuration
public class GatewayBeanConf {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
