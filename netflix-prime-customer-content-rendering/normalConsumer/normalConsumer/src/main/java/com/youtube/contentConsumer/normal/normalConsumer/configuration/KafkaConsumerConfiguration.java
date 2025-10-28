package com.youtube.contentConsumer.normal.normalConsumer.configuration;


import com.youtube.contentConsumer.normal.normalConsumer.model.ContentMetaData;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {


    @Bean
    public ConsumerFactory<String,ContentMetaData> consumerFactory(){
        JsonDeserializer<ContentMetaData> deserializer = new JsonDeserializer<>(ContentMetaData.class,false);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                deserializer.getClass());
        //props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        //props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);

        props.put(JsonDeserializer.TRUSTED_PACKAGES,"com.youtube.contentConsumer.normal.normalConsumer.model");

        return new DefaultKafkaConsumerFactory<>(props,new StringDeserializer(),deserializer);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, ContentMetaData>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ContentMetaData> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
