package cn.kissy.ecommerce.vo;

import cn.kissy.ecommerce.constant.AsyncTaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 异步任务执行信息
 * @ClassName AsyncTaskInfo
 * @Author kingdee
 * @Date 2022/4/24
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsyncTaskInfo {

    /**
     * 异步任务id
     */
    private String taskId;

    /**
     * 异步任务执行状态
     */
    private AsyncTaskStatusEnum status;

    /**
     * 异步任务开始时间
     */
    private Date startTime;

    /**
     * 异步任务结束时间
     */
    private Date endTime;

    /**
     * 异步任务总耗时
     */
    private String totalTime;
}
