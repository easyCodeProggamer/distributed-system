package com.youtube.contentConsumer.normal.normalConsumer.consumer;


import com.youtube.contentConsumer.normal.normalConsumer.model.ContentMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class YouTubeNonPrimeMemberConsumer {
    Logger logger = LoggerFactory.getLogger(YouTubeNonPrimeMemberConsumer.class);

    @Autowired
    RestClient restClient;

    @KafkaListener(
            topics = "myContentTopic",
            groupId = "freeAccount"
    )
    public void consume(ContentMetaData contentMetaData){
        logger.info("_____________________________");
        logger.info("consumed message by free non-prime-member consumer {}", contentMetaData.toString());
        logger.info("_____________________________");
        //Fetch advertisement
        Object add = getAdvertisementData(contentMetaData.getContentLanguage(),"Asia","India");
        //Code for Steaming the content
    }

    public Object getAdvertisementData(String language,String region,String country){
        logger.info("initiate restClient call to fetch the advertisement");
        String uri = "http://localhost:8080/show/Advertisement?language="+language+"&region="+region+"&country="+country;
        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(Object.class);
    }
}