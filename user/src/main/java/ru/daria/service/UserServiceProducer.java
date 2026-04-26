package ru.daria.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.daria.model.User;

@Service
public class UserServiceProducer {
    KafkaTemplate<String, Object> kafkaTemplate;


    public UserServiceProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    public void sendUserClass(String topic, User user) {
        kafkaTemplate.send(topic, user);
    }

    public String testMessage() {
        return "done";
    }

}
