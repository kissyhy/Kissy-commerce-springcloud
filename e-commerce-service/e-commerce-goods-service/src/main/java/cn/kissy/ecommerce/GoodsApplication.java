package cn.kissy.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 商品微服务启动入口
 * 启动依赖组件：Redis + MySQL + Nacos + Kafka + Zipkin
 * http://localhost:8001/ecommerce-goods-service/doc.html
 * @ClassName GoodsApplication
 * @Author kingdee
 * @Date 2022/4/22
 **/
@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }
}
