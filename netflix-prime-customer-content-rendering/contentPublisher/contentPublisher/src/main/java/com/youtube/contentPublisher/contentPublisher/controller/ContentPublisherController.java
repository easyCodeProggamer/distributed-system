package com.youtube.contentPublisher.contentPublisher.controller;

import com.youtube.contentPublisher.contentPublisher.messaging.producer.ContentProducerService;
import com.youtube.contentPublisher.contentPublisher.model.AdvertisementMetaData;
import com.youtube.contentPublisher.contentPublisher.model.ContentMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContentPublisherController {


    @Autowired
    private ContentProducerService contentProducerService;
    @PostMapping("kafka/publish/contentMetaData")
    public ResponseEntity<String> publishContent(@RequestBody ContentMetaData contentMetaData){
        contentProducerService.send(contentMetaData);
        return new ResponseEntity<>("promise", HttpStatus.CREATED);
    }

    @GetMapping("show/Advertisement")
    public ResponseEntity<AdvertisementMetaData> getAdvertisement(@RequestParam String language,
                                                                  @RequestParam String region,
                                                                  @RequestParam String country){
        return new ResponseEntity<>(contentProducerService.getAdvertisement(language,region,country), HttpStatus.OK);
    }

}
