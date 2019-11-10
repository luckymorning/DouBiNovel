package com.cn.lucky.morning.model.web;

import com.cn.lucky.morning.model.common.log.Logs;
import org.slf4j.Logger;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {
    private static Logger logger = Logs.get();
    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        logger.info("项目启动完毕...");
    }
}
