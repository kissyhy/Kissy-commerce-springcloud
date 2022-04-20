package cn.kissy.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 用户账户微服务启动入口
 * swagger: 127.0.0.1:8003/ecommerce-account-service/doc.html
 * @ClassName AccountApplication
 * @Author kingdee
 * @Date 2022/4/19
 **/
@EnableJpaAuditing
@SpringBootApplication
@EnableDiscoveryClient
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
