package ru.daria.user_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.daria.user_service.service.UserServiceProducer;

@Controller
public class UserController {
    UserServiceProducer userServiceProducer;

    public UserController(UserServiceProducer userServiceProducer) {
        this.userServiceProducer = userServiceProducer;
    }

    @GetMapping("/send_user")
    public void sendMessage() {
        System.out.println("Запрос по кафке");
        userServiceProducer.sendMessage("userEvent", "Сообщение");
    }
}
