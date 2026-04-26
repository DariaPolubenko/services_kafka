package ru.daria.handler;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import ru.daria.user_service.model.User;
import tools.jackson.databind.ObjectMapper;

@Service
public class Handler {

//    @KafkaListener(topics = "userEvent", groupId = "notificationGroup") // для гарантии At-least-once
//    public void handleUserEvent(String message, Acknowledgment acknowledgment) {
//        try {
//            System.out.println(message + " " + "пришло от продюссера");
//            //указывает кафке, что сообщение было обработано и синхронизированно. Если произошла ошибка, то в катче исключение ставится
//            acknowledgment.acknowledge(); // at least ones
//        } catch (Exception ex) {
//            System.out.println("Ошибка: " + ex.getMessage());
//        }
//
//    }

//    @KafkaListener(topics = "userEvent", groupId = "notificationGroup")
//    public void handleUserEvent(String message) {
//        System.out.println("Получил сообщение...");
//        System.out.println(message + " " + "пришел от продюссера");
//    }


    //лисенер для объекта User
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "userClass", groupId = "userGroup")
    public void handleUserClass(String userJson) {
        System.out.println("Пришли сырые данные: " + userJson);
        try {
            User user = objectMapper.readValue(userJson, User.class);
            System.out.println("Юзер с id " + user.getId() + " обработан");
        } catch (Exception e) {
            System.out.println("Ошибка при чтении JSON: " + e.getMessage());
        }

    }

}
