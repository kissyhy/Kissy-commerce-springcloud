package cn.kissy.ecommerce.controller;

import cn.kissy.ecommerce.service.NacosClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName NacosClientController
 * @Date 2022/4/5
 * @Author kissy
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/nacos-client")
public class NacosClientController {

    @Autowired
    private NacosClientService nacosClientService;

    /**
     * 根据 serviceId 获取服务所有的实例信息
     * @param serviceId
     * @return
     */
    @GetMapping("/service-instance")
    public List<ServiceInstance> logNacosClientInfo(@RequestParam(defaultValue = "e-commerce-nacos-client") String serviceId){
        log.info("coming in log nacos client info: {}", serviceId);
        return nacosClientService.getNacosClientInfo(serviceId);
    }
}
