package com.calvin.demo;

import com.calvin.demo.service.OpenAiVisionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class VisionController {

    private final OpenAiVisionService visionService;

    public VisionController(OpenAiVisionService visionService) {
        this.visionService = visionService;
    }

    @GetMapping("/describe-image")
    public String describeImage() throws IOException {
        return visionService.describeImage("/images/ghibli-style-image.jpg");
    }
}

