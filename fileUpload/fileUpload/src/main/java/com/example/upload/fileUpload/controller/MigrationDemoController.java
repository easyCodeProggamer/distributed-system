package com.example.upload.fileUpload.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/migration")
public class MigrationDemoController {

    static class SampleEnumHolder {
        public Status status = Status.ACTIVE;
        enum Status { ACTIVE, INACTIVE }
    }

    static class TimeHolder {
        public Instant when = Instant.parse("2025-01-01T00:00:00Z");
    }

    @GetMapping(path = "/jackson2/defaults/time", produces = MediaType.APPLICATION_JSON_VALUE)
    public TimeHolder defaultDateSerialization() {
        // Note: Jackson 2 default WRITE_DATES_AS_TIMESTAMPS is true (timestamps) but Spring Boot config
        // typically registers JavaTimeModule and writes ISO-8601 strings. Kept for demonstration only.
        return new TimeHolder();
    }

    @GetMapping(path = "/jackson2/defaults/enum", produces = MediaType.APPLICATION_JSON_VALUE)
    public SampleEnumHolder defaultEnumSerialization() {
        // In Jackson 2, WRITE_ENUMS_USING_TO_STRING / READ_ENUMS_USING_TO_STRING are false by default.
        return new SampleEnumHolder();
    }

    @PostMapping(path = "/jackson2/trailing-tokens-default", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String trailingTokensDefault(@RequestBody String body) {
        // In Jackson 2, FAIL_ON_TRAILING_TOKENS is DISABLED by default
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.readValue(body, SampleEnumHolder.class);
            return "Parsed OK (default allows trailing tokens in Jackson 2)";
        } catch (JsonProcessingException e) { // checked in Jackson 2
            return "Failed: " + e.getClass().getSimpleName() + " - " + e.getMessage();
        }
    }

    @PostMapping(path = "/jackson2/trailing-tokens-enabled", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String trailingTokensEnabled(@RequestBody String body) {
        // Enabling FAIL_ON_TRAILING_TOKENS will now fail parsing for extra tokens
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        try {
            mapper.readValue(body, SampleEnumHolder.class);
            return "Parsed OK (unexpected)";
        } catch (JsonProcessingException e) {
            return "Failed as expected: " + e.getClass().getSimpleName() + " - " + e.getMessage();
        }
    }
}
