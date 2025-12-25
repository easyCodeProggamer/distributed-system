package com.example.upload.fileUpload.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.autoconfigure.json.JsonMapperBuilderCustomizer;
import org.springframework.boot.jackson.ObjectValueSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


@Configuration
public class JacksonSerializationExample {

    public static class DocDetails {
        @JsonProperty("id")
        public String id;
        @JsonProperty("name")
        public String name;
        public DocDetails() {}
        public DocDetails(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Bean
    public JsonMapperBuilderCustomizer customizer() {
        return builder -> {
            SimpleModule module = new SimpleModule();
            module.addSerializer(DocDetails.class, new ObjectValueSerializer() {
                @Override
                protected void serializeObject(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                    // ...existing code...
                }
            });
            builder.modules(module);
        };
    }

    public String serialize(DocDetails doc) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(doc);
    }

    public DocDetails deserialize(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, DocDetails.class);
    }
}
