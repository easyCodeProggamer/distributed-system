package com.file.upload.consumer.fileUploadConsumer.messaging;

import com.file.upload.consumer.fileUploadConsumer.model.ContentData;
import com.file.upload.consumer.fileUploadConsumer.service.FileProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Service
public class ContentConsumer {
    Logger logger = LoggerFactory.getLogger(ContentConsumer.class);

    @Autowired
    private FileProcessorService fileProcessorService;

    @KafkaListener(
            topics = "uploadContentTopic",
            groupId = "asia"
    )
    public void asiaConsume(ContentData contentMetaData) throws IOException, NoSuchAlgorithmException {
        logger.info("_____________________________");
        logger.info("consumed message by asia consumer {}", contentMetaData.toString());
        logger.info("_____________________________");
        fileProcessorService.processFile(contentMetaData,"asia","temp-consumer-uploads/");
        //Code for Steaming the content
    }

    @KafkaListener(
            topics = "uploadContentTopic",
            groupId = "europe"
    )
    public void europeConsume(ContentData contentMetaData) throws IOException, NoSuchAlgorithmException {
        logger.info("_____________________________");
        logger.info("consumed message by europe consumer {}", contentMetaData.toString());
        logger.info("_____________________________");

        fileProcessorService.processFile(contentMetaData,"europe","temp-consumer-uploads/");
    }

    @KafkaListener(
            topics = "uploadContentTopic",
            groupId = "NorthAmerica"
    )
    public void northAmericaConsume(ContentData contentMetaData) throws IOException, NoSuchAlgorithmException {
        logger.info("_____________________________");
        logger.info("consumed message by NorthAmerica consumer {}", contentMetaData.toString());
        logger.info("_____________________________");

        fileProcessorService.processFile(contentMetaData,"NorthAmerica","temp-consumer-uploads/");

    }


    @KafkaListener(
            topics = "uploadContentTopic",
            groupId = "SouthAmerica"
    )
    public void southAmericaConsume(ContentData contentMetaData) throws IOException, NoSuchAlgorithmException {
        logger.info("_____________________________");
        logger.info("consumed message by SouthAmerica consumer {}", contentMetaData.toString());
        logger.info("_____________________________");

        fileProcessorService.processFile(contentMetaData,"SouthAmerica","temp-consumer-uploads/");
    }

    @KafkaListener(
            topics = "uploadContentTopic",
            groupId = "Oceania"
    )
    public void consume(ContentData contentMetaData) throws IOException, NoSuchAlgorithmException {
        logger.info("_____________________________");
        logger.info("consumed message by Oceania consumer {}", contentMetaData.toString());
        logger.info("_____________________________");

        fileProcessorService.processFile(contentMetaData,"Oceania","temp-consumer-uploads/");
    }
}
