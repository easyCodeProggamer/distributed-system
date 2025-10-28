package com.youtube.contentConsumer.prime.primeConsumer.messaging.consumer;

import com.youtube.contentConsumer.prime.primeConsumer.model.ContentMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class YouTubePrimeMemberConsumer {
    Logger logger = LoggerFactory.getLogger(YouTubePrimeMemberConsumer.class);

    @KafkaListener(
            topics = "myContentTopic",
            groupId = "primeMember"

    )
    public void consume(ContentMetaData contentMetaData){
        logger.info("_____________________________");
        logger.info("consumed message by prime consumer {}", contentMetaData.toString());
        logger.info("_____________________________");
    }
}