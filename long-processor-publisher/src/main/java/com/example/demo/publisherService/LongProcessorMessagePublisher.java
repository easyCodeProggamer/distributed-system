package com.example.demo.publisherService;

import com.example.demo.model.MessageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class LongProcessorMessagePublisher {

    Logger logger = LoggerFactory.getLogger(LongProcessorMessagePublisher.class);
    @Autowired
    @Qualifier("jsonKafkaTemplate")
    @Lazy
    private KafkaTemplate<String, Object> jsonKafkaTemplate;


    public String send(MessageData messageData){
        CompletableFuture<SendResult<String, Object>> future = jsonKafkaTemplate.send("uploadContentTopic",
                messageData);
        future.whenComplete((result,ex)->{
            if (ex == null) {
                logger.info("published message=[ {} ] successfully with offset  {} topic  {} ",messageData,result.getRecordMetadata().offset(),result.getRecordMetadata().topic());
            } else {
                logger.info("Unable to send message=[ {} ] due to : {}" ,messageData, ex.getMessage());
            }
        });
        return "message send";
    }
}
