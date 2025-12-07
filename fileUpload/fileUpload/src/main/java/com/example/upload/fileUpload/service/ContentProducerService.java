package com.example.upload.fileUpload.service;

import com.example.upload.fileUpload.model.ContentData;
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
public class ContentProducerService {

    Logger logger = LoggerFactory.getLogger(ContentProducerService.class);
    @Autowired
    @Qualifier("jsonKafkaTemplate")
    @Lazy
    private KafkaTemplate<String, Object> jsonKafkaTemplate;


    public String send(ContentData contentData){
        CompletableFuture<SendResult<String, Object>> future = jsonKafkaTemplate.send("uploadContentTopic",
                contentData);
        future.whenComplete((result,ex)->{
            if (ex == null) {
                logger.info("published message=[ {} ] successfully with offset  {} topic  {} ",contentData,result.getRecordMetadata().offset(),result.getRecordMetadata().topic());
            } else {
                logger.info("Unable to send message=[ {} ] due to : {}" ,contentData, ex.getMessage());
            }
        });
        return "message send";
    }

}
