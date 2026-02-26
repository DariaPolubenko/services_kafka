package ru.daria.handler;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Handler {

    @KafkaListener(topics = "userEvent", groupId = "notificationGroup")
    public void handleUserEvent(String message) {
        System.out.println(message + " " + "пришло от продюссера");

    }

}
