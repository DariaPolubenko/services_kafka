package ru.daria.controller;

import org.springframework.web.bind.annotation.*;
import ru.daria.model.User;
import ru.daria.service.UserServiceProducer;

@RestController
public class UserController {
    UserServiceProducer userServiceProducer;

    public UserController(UserServiceProducer userServiceProducer) {
        this.userServiceProducer = userServiceProducer;
    }

    @GetMapping("/send_user")
    public void sendMessage(@RequestParam String message) {
        System.out.println("Запрос по кафке");
        userServiceProducer.sendMessage("userEvent", message);
    }

    @PostMapping("/send_user_class")
    public void sendUserClass(@RequestBody User user) {
        System.out.println("Передаю юзера кафке...");
        userServiceProducer.sendUserClass("userClass", user);
    }
}
//unit - позволять теститьрвоать системы через отправление запроса в контроллер (но он ненсаточщий)