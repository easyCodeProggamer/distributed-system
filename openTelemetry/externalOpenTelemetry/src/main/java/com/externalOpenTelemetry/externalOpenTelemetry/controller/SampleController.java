package com.externalOpenTelemetry.externalOpenTelemetry.controller;

import com.externalOpenTelemetry.externalOpenTelemetry.service.MySampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    private MySampleService mySampleService;

    @GetMapping("/sample/external")
    public String sampleExternal(){
        logger.info("welcome to external service");
        return mySampleService.mySampleMethod();
    }
}
