package com.example.upload.fileUpload.service;

import com.example.upload.fileUpload.model.ContentData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentProducerServiceTests {

    @Mock
    private KafkaTemplate<String, Object> jsonKafkaTemplate;

    @InjectMocks
    private ContentProducerService service;

    @Test
    void send_publishesMessage_evenIfKafkaFails() {
        // Arrange: fail the future to avoid needing Kafka RecordMetadata
        when(jsonKafkaTemplate.send(eq("uploadContentTopic"), any()))
                .thenReturn(CompletableFuture.failedFuture(new RuntimeException("Kafka down")));

        ContentData content = new ContentData();
        content.setFileId("file-1");
        content.setFileName("demo.bin");

        // Act
        String result = service.send(content);

        // Assert
        assertThat(result).isEqualTo("message send");
        verify(jsonKafkaTemplate, times(1)).send(eq("uploadContentTopic"), any());
    }
}
