package com.example.demo.model;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MyCustomThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private String threadNamePrefix = "";

    public MyCustomThreadFactory(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, threadNamePrefix + threadNumber.getAndIncrement());
        thread.setPriority(Thread.MAX_PRIORITY);
        return thread;
    }
}
