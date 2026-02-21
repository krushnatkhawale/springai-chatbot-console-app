package com.krushnatkhawale.springaichatbot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("dev")  // Test local profile
class ChatbotServiceTest {
    @Autowired
    private ChatbotService service;

    @Test
    void testResponse() {
        String response = service.getResponse("Hello");
        assertNotNull(response);
        assertFalse(response.isBlank());
    }
}