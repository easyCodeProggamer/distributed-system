package com.example.upload.fileUpload.rest;


import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientExample {

    @Bean
    public RestClient restClient() {
        RestClient.Builder builder = RestClient.builder();
        return builder.build();
    }



    public String getDocDetails() {
        RestClient client = restClient();
        return client.get()
                .uri("http://localhost:8081/getDocDetails")
                .retrieve()
                .body(String.class);
    }
}
