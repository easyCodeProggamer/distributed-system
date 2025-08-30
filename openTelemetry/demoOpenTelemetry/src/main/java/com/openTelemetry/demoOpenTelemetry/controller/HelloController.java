package com.openTelemetry.demoOpenTelemetry.controller;

import com.openTelemetry.demoOpenTelemetry.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    private SampleService sampleService;
    @GetMapping("/hello")
    public String hello() {
        logger.info("Handling /hello request");
        String response = sampleService.getData();
        logger.info("response {}",response);
        return response;
    }

    @GetMapping("/sample")
    public String sample() {
        logger.info("Handling /sample request");
        return "Hello from Spring Boot with OpenTelemetry RestClient!";
    }

}