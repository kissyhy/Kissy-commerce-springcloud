package cn.kissy.ecommerce.controller;

import cn.kissy.ecommerce.service.SleuthTraceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 打印跟踪信息
 * @ClassName SleuthTraceInfoController
 * @Author kingdee
 * @Date 2022/4/18
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/sleuth")
public class SleuthTraceInfoController {

    @Autowired
    private SleuthTraceInfoService traceInfoService;

    /**
     * 打印日志跟踪信息 spanId和 traceId
     */
    @GetMapping("/trace-info")
    public void logCurrentTraceInfo(){
        traceInfoService.logCurrentTraceInfo();
    }
}
