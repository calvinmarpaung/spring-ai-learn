package com.calvin.demo;

import org.springframework.ai.chat.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIController {
    @Autowired
    ChatClient chatClient;

    @GetMapping("/prompt")
    public String prompt(@RequestParam String message) {
        return chatClient.call(message);
    }
}
