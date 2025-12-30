package com.example.upload.fileUpload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MigrationDemoTests {

    static class Holder { public String name; }

    @Test
    void jackson2TrailingTokensDisabledByDefault_allowsExtraContent() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonWithTrailing = "{\"name\":\"x\"} true";
        assertDoesNotThrow(() -> mapper.readValue(jsonWithTrailing, Holder.class));
    }

    @Test
    void enablingFailOnTrailingTokensCausesFailureInJackson2() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        String jsonWithTrailing = "{\"name\":\"x\"} true";
        assertThrows(JsonProcessingException.class, () -> mapper.readValue(jsonWithTrailing, Holder.class));
    }
}
