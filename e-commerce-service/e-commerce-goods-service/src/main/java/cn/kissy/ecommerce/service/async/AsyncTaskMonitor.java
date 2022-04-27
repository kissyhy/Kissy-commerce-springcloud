package cn.kissy.ecommerce.service.async;

import cn.kissy.ecommerce.constant.AsyncTaskStatusEnum;
import cn.kissy.ecommerce.vo.AsyncTaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 异步任务执行监控切面
 * @ClassName AsyncTaskMonitor
 * @Author kingdee
 * @Date 2022/4/25
 **/
@Slf4j
@Aspect
@Component
public class AsyncTaskMonitor {

    /**
     * 注入异步任务管理器
     */
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    /**
     * 异步任务执行的环绕切面
     * 环绕切面让我们可以在方法执行之前和执行之后做一些 <额外> 的操作
     * @param joinPoint
     * @return
     */
    @Around("execution(* cn.kissy.ecommerce.service.async.AsyncServiceImpl.*(..))")
    public Object taskHandle(ProceedingJoinPoint joinPoint){
        // 获取taskId, 调用异步任务的第二个参数
        String taskId = joinPoint.getArgs()[1].toString();

        // 获取任务信息，在提交任务的时候就已经放入到容器中了
        AsyncTaskInfo taskInfo = asyncTaskManager.getTaskInfo(taskId);
        log.info("AsyncTaskMonitor is monitoring async task: [{}]", taskId);

        // 设置为运行状态，并重新放入容器
        taskInfo.setStatus(AsyncTaskStatusEnum.RUNNING);
        asyncTaskManager.setTaskInfo(taskInfo);

        AsyncTaskStatusEnum status;
        // 异步任务执行的结果
        Object result;
        try{
            // 执行异步任务
            result = joinPoint.proceed();
            status = AsyncTaskStatusEnum.SUCCESS;
        }catch (Throwable ex){
            // 异步任务出现了异常
            result = null;
            status = AsyncTaskStatusEnum.FAILED;
            log.error("AsyncTaskMonitor: async task [{}] is failed, Error Info: [{}]",
                    taskId, ex.getMessage(), ex);
        }

        // 设置异步任务其他的信息，再次重新放入到容器中
        taskInfo.setEndTime(new Date());
        taskInfo.setStatus(status);
        taskInfo.setTotalTime(String.valueOf(
                taskInfo.getEndTime().getTime() - taskInfo.getStartTime().getTime()
        ));
        asyncTaskManager.setTaskInfo(taskInfo);
        return result;
    }
}
