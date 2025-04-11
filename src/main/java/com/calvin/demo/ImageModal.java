//package com.calvin.demo;
//import org.springframework.ai.chat.messages.UserMessage;
//import org.springframework.ai.chat.model.ChatModel;
//import org.springframework.ai.chat.model.ChatResponse;
//import org.springframework.ai.chat.prompt.Prompt;
//import org.springframework.ai.chat.messages.*;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.util.MimeTypeUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//public class ImageModal {
//
//    private final ChatModel chatClient;
//
//    public ImageModal(ChatModel chatClient) {
//        this.chatClient = chatClient;
//    }
//
//    @PostMapping("/image/chat")
//    public String generateRespImage(@RequestParam String query) throws IOException {
//        return getImageChatReader(query);
//    }
//
//    public String getImageChatReader(String query) throws IOException {
//        // Load the image as byte[]
//        byte[] data = new ClassPathResource("images/ghibli-style-image.jpg").getContentAsByteArray();
//
//        // Create media object
//        Media imageMedia = new Media(MimeTypeUtils.IMAGE_JPEG, data);
//
//        // Create user message with the image
//        UserMessage userMessage = new UserMessage(query); // just the text
//
//        ChatResponse response = chatClient.call(new Prompt(userMessage));
//
//        String output = response.getResult().getOutput().getText(); // may vary
//
//        return output;
//    }
//
//}
//
