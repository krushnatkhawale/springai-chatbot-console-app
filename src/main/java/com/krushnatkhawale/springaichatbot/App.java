package com.krushnatkhawale.springaichatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        ChatbotService service = context.getBean(ChatbotService.class);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chatbot ready. Type 'exit' to quit.");
        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) break;
            String response = service.getResponse(input);
            System.out.println("Bot: " + response);
        }
    }
}
