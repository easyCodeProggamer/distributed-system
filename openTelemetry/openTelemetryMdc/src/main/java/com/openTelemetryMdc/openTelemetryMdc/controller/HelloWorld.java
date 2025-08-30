package com.openTelemetryMdc.openTelemetryMdc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class HelloWorld {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    @GetMapping("myHelloMdc")
    public String helloWld(){
        MDC.put("trace_id", UUID.randomUUID().toString());
        MDC.put("span_id", UUID.randomUUID().toString());
        logger.info("hello world");
        MDC.clear();
        return "success";
    }
}
