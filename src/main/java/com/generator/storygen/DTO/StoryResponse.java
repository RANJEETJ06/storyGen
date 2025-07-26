package com.generator.storygen.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryResponse {
    private String story;

    public StoryResponse(String story) {
        this.story = story;
    }
}
