package com.youtube.contentPublisher.contentPublisher.messaging.producer;

import com.youtube.contentPublisher.contentPublisher.model.AdvertisementMetaData;
import com.youtube.contentPublisher.contentPublisher.model.ContentMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class ContentProducerService {

    Logger logger = LoggerFactory.getLogger(ContentProducerService.class);
    @Autowired
    @Qualifier("jsonKafkaTemplate")
    @Lazy
    private KafkaTemplate<String, Object> jsonKafkaTemplate;


    public String send(ContentMetaData contentMetaData){
        CompletableFuture<SendResult<String, Object>> future = jsonKafkaTemplate.send("myContentTopic",
                contentMetaData);
        future.whenComplete((result,ex)->{
            if (ex == null) {
                logger.info("published message=[ {} ] successfully with offset  {} topic  {} ",contentMetaData,result.getRecordMetadata().offset(),result.getRecordMetadata().topic());
            } else {
                logger.info("Unable to send message=[ {} ] due to : {}" ,contentMetaData, ex.getMessage());
            }
        });
        return "message send";
    }

    public AdvertisementMetaData getAdvertisement(String language,String region,String country){
        AdvertisementMetaData advertisementMetaData = new AdvertisementMetaData();
        advertisementMetaData.setAdvertisementId("demo123");
        advertisementMetaData.setSponsorshipDetail("Sample advertisement demo");

        ContentMetaData addContent = new ContentMetaData();
        addContent.setContentType("Advertisement");
        addContent.setContentLanguage("Java Course");
        addContent.setContentLocation("India/Bangalore");
        advertisementMetaData.setContent(addContent);
        return advertisementMetaData;
    }

}
