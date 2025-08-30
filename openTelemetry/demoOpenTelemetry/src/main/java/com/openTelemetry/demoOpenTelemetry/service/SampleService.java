package com.openTelemetry.demoOpenTelemetry.service;

import com.openTelemetry.demoOpenTelemetry.controller.HelloController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SampleService {

    private static final Logger logger = LoggerFactory.getLogger(SampleService.class);

    @Autowired
    RestClient restClient;

    public String getData(){
        logger.info("initiate restClient call");

        return restClient.get()
                .uri("http://localhost:8081/sample/external")
                .retrieve()
                .body(String.class);
    }

}
