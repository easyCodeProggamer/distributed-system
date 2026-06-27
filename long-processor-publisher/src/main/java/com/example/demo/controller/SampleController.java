package com.example.demo.controller;


import com.example.demo.model.MessageData;
import com.example.demo.publisherService.LongProcessorMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SampleController {

    @Autowired
    private LongProcessorMessagePublisher longProcessorMessagePublisher;

    @PostMapping("/process/message")
    public UUID publishProcessHeavy(@RequestBody MessageData messageData){
        longProcessorMessagePublisher.send(messageData);
        return UUID.randomUUID();
    }
}
