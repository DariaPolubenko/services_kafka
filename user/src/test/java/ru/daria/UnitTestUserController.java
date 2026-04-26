package ru.daria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.daria.service.UserServiceProducer;
import ru.daria.model.User;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UnitTestUserController {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserServiceProducer userServiceProducer;

    private User user;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        user = new User(1, "Test", "Test", 28);
    }

    @Test
    public void testSendMessage() throws Exception {
        doNothing().when(userServiceProducer).sendMessage("Test", "test message");
        mockMvc.perform(get("/send_user").param("message", "test message")).andExpect(status().isOk());
    }

    @Test
    public void testSendUserClass() throws Exception {
        doNothing().when(userServiceProducer).sendUserClass("test", user);
        mockMvc.perform(post("/send_user_class").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user))).andExpect(status().isOk());
    }

}
