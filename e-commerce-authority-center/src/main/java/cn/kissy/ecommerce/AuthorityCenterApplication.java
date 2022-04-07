package cn.kissy.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 授权中心启动入口
 * @ClassName AuthorityCenterApplication
 * @Author kingdee
 * @Date 2022/4/7
 * @Version 1.0
 **/
@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
public class AuthorityCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorityCenterApplication.class, args);
    }
}
