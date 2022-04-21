package cn.kissy.ecommerce.conf;

import cn.kissy.ecommerce.filter.LoginUserInfoInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Web Mvc 配置，写好拦截器一定要配置之后才会生效
 * @ClassName ImoocWebMvcConfig
 * @Author kingdee
 * @Date 2022/4/19
 **/
@Configuration
public class ImoocWebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 添加拦截器配置
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 添加用户身份统一登录拦截的拦截器
        registry.addInterceptor(new LoginUserInfoInterceptor()).addPathPatterns("/**").order(0);
    }

    /**
     * 让MVC加载Swagger的静态资源
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        super.addResourceHandlers(registry);
    }
}
