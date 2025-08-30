package com.externalOpenTelemetry.externalOpenTelemetry.service;

import com.externalOpenTelemetry.externalOpenTelemetry.controller.SampleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MySampleService {

    private static final Logger logger = LoggerFactory.getLogger(MySampleService.class);

    public String mySampleMethod(){
        logger.info("Some service call");
        return "external call success";
    }
}
