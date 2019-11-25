package com.cn.lucky.morning.model.web;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class TestAsynTask {

    @Async
    public Future<String> test(String str,Long sleep){
        try {
            Thread.sleep(sleep);
            return new AsyncResult<>(str);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new AsyncResult<>(e.getMessage());
        }
    }
}
