package com.example.demo.configuration;


import com.example.demo.model.MessageData;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration {


    @Bean
    public ConsumerFactory<String,MessageData> consumerFactory(){
        JsonDeserializer<MessageData> deserializer = new JsonDeserializer<>(MessageData.class,false);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                deserializer.getClass());
        //props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        //props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);

        props.put(JsonDeserializer.TRUSTED_PACKAGES,"com.example.demo.model.MessageData");


        return new DefaultKafkaConsumerFactory<>(props,new StringDeserializer(),deserializer);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, MessageData>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MessageData> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

}
