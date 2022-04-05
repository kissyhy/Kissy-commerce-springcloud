package cn.kissy.ecommerce.advice;

import cn.kissy.ecommerce.annotation.IgnoreResponseAdvice;
import cn.kissy.ecommerce.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 实现统一响应
 * 在Controller返回的时候对data数据进行拦截与包装
 * 拦截：ResponseBodyAdvice 可以对返回的Body进行拦截
 * @RestControllerAdvice 给拦截增加一个范围：cn.kissy.ecommerce
 * @ClassName CommonResponseDataAdvice
 * @Date 2022/4/5
 * @Author kissy
 * @Version 1.0
 **/
@RestControllerAdvice(value = "cn.kissy.ecommerce")
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 判断是否需要对响应进行包装处理
     * 如若需要包装，我们就会进入 beforeBodyWrite 这个方法中进行包装
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    @SuppressWarnings("all")
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {

        // 先去获取对应方法调用Controller的类，同时这个类标识了这个注解，我们就不做特殊处理
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)){
            return false;
        }

        // 先去获取对应方法，同时这个方法标识了这个注解，我们就不做特殊处理
        if (methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)){
            return false;
        }

        return true;
    }

    /**
     * 如若返回对象不是CommonResponse，就把拦截的Object o -> CommonResponse
     * @param o
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        // 定义最终的返回对象
        CommonResponse<Object> response = new CommonResponse<>(0, "");

        // 这里就是把 Object o -> CommonResponse 返回给前端
        if (null == o){
            return response;
        } else if (o instanceof CommonResponse){
            response = (CommonResponse<Object>) o;
        } else {
            response.setData(o);
        }
        return response;
    }
}
