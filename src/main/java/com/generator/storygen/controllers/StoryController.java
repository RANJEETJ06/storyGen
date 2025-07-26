package com.generator.storygen.controllers;

import com.generator.storygen.DTO.StoryResponse;
import com.generator.storygen.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @GetMapping("/generate-story")
    public ResponseEntity<StoryResponse> generateStory(
            @RequestParam(value = "character", defaultValue = "Alex") String character,
            @RequestParam(value = "setting", defaultValue = "a magical forest") String setting,
            @RequestParam(value = "theme", defaultValue = "adventure") String theme
    ) {
        StoryResponse storyResponse=new StoryResponse(storyService.generateStory(character, theme, setting));
        return new ResponseEntity<>(storyResponse, HttpStatus.OK);
    }

}
