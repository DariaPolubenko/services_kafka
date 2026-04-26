package ru.daria.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration { //конфиг продюссера

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> hashmap = new HashMap<>();
        hashmap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        hashmap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        hashmap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        hashmap.put(ProducerConfig.ACKS_CONFIG, "all"); //At-least-once, подтверждения от брокера
        //hashmap.put(ProducerConfig.ACKS_CONFIG, "0"); // At-most-once, не ждет подтверждение от брокера
        hashmap.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE); //At-least-once, повторные отправки

        return new DefaultKafkaProducerFactory<>(hashmap);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<String, Object>(producerFactory());
    }

}


