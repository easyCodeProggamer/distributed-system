package com.example.upload.fileUpload.jackson;

import com.fasterxml.jackson.annotation.JsonProperty; // annotations stay in com.fasterxml
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.jackson.JsonObjectSerializer;
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

    // Spring Boot 3 + Jackson 2 example: register a custom serializer using Jackson 2 types
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            SimpleModule module = new SimpleModule();
            module.addSerializer(DocDetails.class, new JsonObjectSerializer() {
                @Override
                protected void serializeObject(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                    if (value instanceof DocDetails) {
                        DocDetails doc = (DocDetails) value;
                        jgen.writeStartObject();
                        jgen.writeStringField("id", doc.id);
                        jgen.writeStringField("name", doc.name);
                        jgen.writeEndObject();
                    }
                }
            });
            builder.modules(module);
        };
    }

    // Demonstrates checked exceptions in Jackson 2
    public String serialize(DocDetails doc) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(doc);
    }

    public DocDetails deserialize(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, DocDetails.class);
    }

    // Additional Jackson 2 patterns that will change in Jackson 3

    // 1) Deprecated/removed in Jackson 3: canSerialize/canDeserialize
    public boolean canSerializeDocDetailsWithJackson2() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.canSerialize(DocDetails.class);
    }

    // 2) Mutable ObjectMapper configuration (builder/immutable in Jackson 3)
    public ObjectMapper buildMutableMapperJackson2() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        mapper.configure(DeserializationFeature.FAIL_ON_TRAILING_TOKENS, false);
        return mapper;
    }

    // 3) JsonFactory#setCodec(ObjectMapper) association (removed in Jackson 3)
    public String serializeWithJsonFactoryAndCodec(DocDetails doc) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = new JsonFactory();
        factory.enable(JsonParser.Feature.ALLOW_COMMENTS);
        factory.setCodec(mapper); // Associated codec is removed in Jackson 3
        return mapper.writeValueAsString(doc);
    }

    // 4) Polymorphic typing using LaissezFaireSubTypeValidator (not public in Jackson 3)
    public String serializeWithDefaultTyping(Object value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS);
        return mapper.writeValueAsString(value);
    }
}
