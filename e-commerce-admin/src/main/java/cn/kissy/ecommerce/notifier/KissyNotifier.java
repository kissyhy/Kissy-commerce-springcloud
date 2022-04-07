package cn.kissy.ecommerce.notifier;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 自定义告警, 继承SpringBootAdmin包下的 事件通知器的抽象类
 * @ClassName KissyNotifier
 * @Author kingdee
 * @Date 2022/4/6
 * @Version 1.0
 **/
@Slf4j
@Component
@SuppressWarnings("all")
public class KissyNotifier extends AbstractEventNotifier {

    protected KissyNotifier(InstanceRepository repository) {
        super(repository);
    }

    /**
     * 实现对事件的通知
     * @param event
     * @param instance
     * @return
     */
    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            // 状态发生了改变, 打印：实例名字，事件的实例，事件的状态
           if (event instanceof InstanceStatusChangedEvent){
                log.info("Instance Status Change: [{}], [{}], [{}]",
                        instance.getRegistration().getName(), event.getInstance(),
                        ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus());
           } else {
               // 如若状态没有发生改变，打印：实例名字，事件的实例，事件的类型
               log.info("Instance Info: [{}], [{}], [{}]",
                       instance.getRegistration().getName(), event.getInstance(), event.getType());
           }
        });
    }
}
