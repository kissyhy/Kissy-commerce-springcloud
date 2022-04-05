package cn.kissy.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 工程启动入口
 * @ClassName NacosClientApplication
 * @Date 2022/4/5
 * @Author kissy
 * @Version 1.0
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class NacosClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosClientApplication.class, args);
    }
}
