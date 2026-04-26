package ru.daria.kafka.test.containers;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestKafka extends KafkaTestSettings {

    @BeforeEach
    @Override
    public void setUp () {
        super.setUp();
        createTopic("topic", 1, 1);
    }

    @Test
    public void myTestKafka() {
        kafkaConsumer.subscribe(Collections.singletonList("topic"));
        kafkaProducer.send(new ProducerRecord<>("topic", "key", "message"));

        ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(5));
        consumerRecords.count();

        Assertions.assertEquals(1, consumerRecords.count());
    }

    @Test
    public void testMultiplyMessage() {
        kafkaConsumer.subscribe(Collections.singletonList("topic"));
        for(int i = 0; i < 3; i++) {
            kafkaProducer.send(new ProducerRecord<>("topic", "key" + i, "message" + i));
        }
        ConsumerRecords<String, String> consumerRecords = await().atMost(Duration.ofSeconds(5)).until(()-> {
            ConsumerRecords<String, String> pollAt = kafkaConsumer.poll(Duration.ofSeconds(1));
            return !pollAt.isEmpty() ? pollAt : null;
        }, notNullValue());

        consumerRecords.forEach(element -> {
            System.out.println("ТЕСТ "+ element.key() + "\n");
            System.out.println(element.value());
        });

        Assertions.assertEquals(3, consumerRecords.count(), "Ожидалось 3 сообщения");
    }

    @AfterEach
    public void afterEach() {
        kafkaConsumer.close();
    }

    public void testMessageIsRetry() {
        kafkaConsumer.subscribe(Collections.singletonList("topic"));
        AtomicInteger attempt = new AtomicInteger();
        //when()
        kafkaProducer.send(new ProducerRecord<>("topic", "key", "message"));
        await().atMost(Duration.ofSeconds(10)).until(() -> {
            return attempt.get() >= 3;
        });
        //verify()
    }

}
