package cn.kissy.ecommerce.advice;

import cn.kissy.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常捕获处理
 * @ClassName GlobalExceptionAdvice
 * @Date 2022/4/5
 * @Author kissy
 * @Version 1.0
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice{

    @ExceptionHandler(value = Exception.class)
    public CommonResponse<String> handlerCommerceException(HttpServletRequest request,
                                                           Exception exception){
        // 最终统一返回一个业务错误逻辑 business error
        CommonResponse<String> response = new CommonResponse<>(-1, "business error");
        // set 到 exception的Data里
        response.setData(exception.getMessage());
        log.error("commerce service has error: [{}]", exception.getMessage(), exception);
        return response;
    }
}
