package com.krushnatkhawale.springaichatbot;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChatbotService {
    private final ChatClient chatClient;

    public ChatbotService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String getResponse(String userMessage) {
        PromptTemplate template = new PromptTemplate("You are a friendly chatbot. Respond to: {message}");
        Prompt prompt = template.create(Map.of("message", userMessage));
        return chatClient.prompt(prompt).call().content();
    }
}