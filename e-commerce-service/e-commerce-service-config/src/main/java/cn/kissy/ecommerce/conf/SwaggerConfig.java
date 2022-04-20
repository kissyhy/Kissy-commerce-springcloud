package cn.kissy.ecommerce.conf;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 * 原生：/swagger-ui.html
 * 美化：/doc.html
 * @ClassName SwaggerConfig
 * @Author kingdee
 * @Date 2022/4/19
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Swagger 实例，Bean是Docket，所以通过配置Docket实例来配置Swagger
     * @return
     */
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                // 展示在Swagger页面上的自定义工程描述信息
                .apiInfo(apiInfo())
                // 选择展示哪些接口
                .select()
                // 只有 cn.kissy.ecommerce 包内的才去展示
                .apis(RequestHandlerSelectors.basePackage("cn.kissy.ecommerce"))
                .paths(PathSelectors.any())
                .build();
    }


    /**
     * Swagger 描述信息
     * @return
     */
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("imooc-micro-service")
                .description("e-commerce-springcloud-service")
                .contact(new Contact("kissy", "www.baidu.com", "kissyhy@naver.com"))
                .version("1.0")
                .build();
    }
}
