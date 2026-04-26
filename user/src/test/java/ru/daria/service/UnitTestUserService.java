package ru.daria.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnitTestUserService {

    @Mock //объект связанный с мок, он не будет иметь настоящей реализации
    KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    UserServiceProducer userServiceProducer1;

    @Mock
    UserServiceProducer userServiceProducer2;

    @Test
    public void shouldBeTestKafkaMessage() {
        userServiceProducer1.sendMessage("topic", "data");
        verify(kafkaTemplate, times(1)).send("topic", "data");
    }

    @Test
    public void testKafka() {
        when(userServiceProducer2.testMessage()).thenReturn("Petya");
        //System.out.println(userServiceProducer.testMessage());
        Assertions.assertEquals("Petya", userServiceProducer2.testMessage());
    }
}
