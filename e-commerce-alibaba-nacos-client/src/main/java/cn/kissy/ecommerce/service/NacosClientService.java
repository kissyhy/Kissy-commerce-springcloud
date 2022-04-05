package cn.kissy.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务发现
 * @ClassName NacosClientService
 * @Date 2022/4/5
 * @Author kissy
 * @Version 1.0
 **/
@Slf4j
@Service
public class NacosClientService {

    /**
     * 服务发现的客户端
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * ServiceInstance: 服务实例
     * 打印 Nacos Client 信息到日志中
     * @param serviceId
     * @return
     */
    public List<ServiceInstance> getNacosClientInfo(String serviceId){
        log.info("request nacos client to get service instance info: [{}]", serviceId);
        return discoveryClient.getInstances(serviceId);
    }
}
