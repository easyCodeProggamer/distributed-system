package com.example.demo.consumer;


import com.example.demo.model.MessageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;


@Service
public class LongProcessingConsumer {
    Logger logger = LoggerFactory.getLogger(LongProcessingConsumer.class);


    @KafkaListener(
            topics = "uploadContentTopic",
            groupId = "group1",
            properties = {"max.poll.interval.ms=910001"}

    )
    public void consume(MessageData messageData,
                        Acknowledgment acknowledgment
                        ) throws InterruptedException {
        logger.info("_____________________________");
        logger.info("consumed message by group1 {}", messageData.toString());
        logger.info("_____________________________");
        acknowledgment.acknowledge();
        getAdvertisementData();

    }



    public void getAdvertisementData() throws InterruptedException {
        logger.info("processing group1 message starts");
        Thread.sleep(600000);
        logger.info("6.6mins break, ends");

    }


}