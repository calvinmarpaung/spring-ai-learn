package com.calvin.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiVisionService {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    private final WebClient webClient;

    public OpenAiVisionService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(OPENAI_URL)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String describeImage(String imagePath) throws IOException {
        // Load image and encode to base64
        ClassPathResource imageResource = new ClassPathResource(imagePath);
        byte[] imageBytes = imageResource.getInputStream().readAllBytes();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        String imageMimeType = imagePath.endsWith(".png") ? "image/png" : "image/jpeg";

        // Build payload
        Map<String, Object> body = Map.of(
                "model", "gpt-4o",
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", List.of(
                                        Map.of("type", "text", "text", "As gen Z, describe this image. What style artist is it?"),
                                        Map.of("type", "image_url", "image_url", Map.of(
                                                "url", "data:" + imageMimeType + ";base64," + base64Image
                                        ))
                                )
                        )
                ),
                "max_tokens", 2,
                "temperature", 0.5
        );

        // Make API call
        return webClient.post()
                .headers(headers -> headers.setBearerAuth(openAiApiKey))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .map(responseBody -> {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                    if (choices != null && !choices.isEmpty()) {
                        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                        return message != null ? (String) message.get("content") : "Message content is null.";
                    }
                    return "No choices found in response.";
                })
                .onErrorResume(error -> Mono.just("OpenAI API error: " + error.getMessage()))
                .block();
    }
}
