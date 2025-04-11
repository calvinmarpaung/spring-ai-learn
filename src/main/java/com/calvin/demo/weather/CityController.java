package com.calvin.demo.weather;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {

    @Autowired
    private ChatClient chatClient;

    @GetMapping("/cities")
    public String getCities(@RequestParam (value =  "message") String message) {
        SystemMessage systemMessage = new SystemMessage(
                "You are a helpful assistant that answering about weather cities through api in getWeather function and also show the latitude and longitude");
        UserMessage userMessage = new UserMessage(message);

        OpenAiChatOptions.builder()
                .withFunction("getWeather")
                .build();

        var chatResponse = chatClient.call(new Prompt(List.of(systemMessage, userMessage)));
        return chatResponse.getResult().getOutput().getContent();
    }
}
