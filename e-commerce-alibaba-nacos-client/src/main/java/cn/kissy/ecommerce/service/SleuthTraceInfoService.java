package cn.kissy.ecommerce.service;

import brave.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 使用代码更直观的看到 Sleuth 生成的相关跟踪信息
 * @ClassName SleuthTraceInfoService
 * @Author kingdee
 * @Date 2022/4/18
 * @Version 1.0
 **/
@Service
@Slf4j
public class SleuthTraceInfoService {

    /**
     * brave.Tracer 跟踪对象
     */
    @Autowired
    private Tracer tracer;

    /**
     * 打印当前的跟踪信息到日志中
     */
    public void logCurrentTraceInfo(){
        log.info("Sleuth trace id: [{}]", tracer.currentSpan().context().traceId());
        log.info("Sleuth span id: [{}]", tracer.currentSpan().context().spanId());
    }
}
