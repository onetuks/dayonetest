package com.onetuks.dayonetest;

import com.onetuks.dayonetest.service.KafkaConsumerService;
import com.onetuks.dayonetest.service.KafkaProducerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class KafkaConsumerApplicationTests extends IntegrationTest {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @MockBean
    private KafkaConsumerService kafkaConsumerService;

    @Test
    void kafkaSendAndConsumeTest() {
        // Given
        String topic = "test-topic";
        String expectValue = "expect-value";

        // When
        kafkaProducerService.send(topic, expectValue);

        // Then
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(kafkaConsumerService, Mockito.timeout(5000).times(1))
                .process(stringArgumentCaptor.capture());

        Assertions.assertEquals(expectValue, stringArgumentCaptor.getValue());
    }

}
