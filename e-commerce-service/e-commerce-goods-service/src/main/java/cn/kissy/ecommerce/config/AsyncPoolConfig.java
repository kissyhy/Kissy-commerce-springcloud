package cn.kissy.ecommerce.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义异步任务线程池，异常任务捕获处理器
 * @ClassName AsyncPoolConfig
 * @Author kingdee
 * @Date 2022/4/24
 **/
@Slf4j
@EnableAsync // 开启Spring异步任务支持
@Configuration
public class AsyncPoolConfig implements AsyncConfigurer {

    /**
     * 将自定义线程池注入到 Spring 容器中
     * @return
     */
    @Bean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 初始线程大小
        executor.setCorePoolSize(10);
        // 最大线程大小
        executor.setMaxPoolSize(20);
        // 阻塞队列
        executor.setQueueCapacity(20);
        // 线程存活时间
        executor.setKeepAliveSeconds(60);
        //给每一个线程启动的时候都会起一个前缀的名字
        executor.setThreadNamePrefix("kissy-");

        // 等待所有任务结束时再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置线程池任务的等待时间，超过这个时间强制销毁，防止死循环
        executor.setAwaitTerminationSeconds(60);
        // 定义拒绝策略
        executor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        // 初始化线程池, 初始化core线程
        executor.initialize();
        return executor;
    }

    /**
     * 指定系统中的异步任务在出现异常时使用到的处理器
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

    /**
     * 异步任务异常捕获处理器
     */
    @SuppressWarnings("all")
    class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler{

        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
            throwable.printStackTrace();
            log.error("Async Error: [{}], Method: [{}], Param: [{}]",
                    throwable.getMessage(), method.getName(), JSON.toJSONString(objects));

            //TODO 发送邮件或短信，做进一步的报警处理
        }
    }
}
