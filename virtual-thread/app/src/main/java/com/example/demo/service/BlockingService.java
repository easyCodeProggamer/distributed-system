package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class BlockingService{

    private static final Logger LOGGER = Logger.getLogger(BlockingService.class.getName());

    public String process() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long sum = 0;
        for (int i = 0; i < 1_000_000; i++) {
            sum += i;
        }
        LOGGER.info("Current Task is processed by thread: " + Thread.currentThread().getName());
        return "OK";
    }


}