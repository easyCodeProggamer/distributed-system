package com.example.demo.config;

import com.example.demo.model.MyCustomThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
public class ThreadExecutorConfig {

    @Bean
    public ExecutorService virtualThreadExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    public ExecutorService osPlatformThreadExecutor() {
        //ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //ExecutorService executorService = Executors.newFixedThreadPool(5,threadFactory);
        return Executors.newFixedThreadPool(200);
    }

    @Bean
    public ExecutorService customOsPlatformThreadExecutorWithThreadFactory() {
        MyCustomThreadFactory myCustomThreadFactory = new MyCustomThreadFactory("mrigank-custom-thread");
        return Executors.newFixedThreadPool(200,myCustomThreadFactory);
    }
}