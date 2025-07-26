package com.generator.storygen.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryRequest {
    private String mainCharacter;
    private String theme;
    private String settings;
    public StoryRequest() {
        this.mainCharacter = "Alex";
        this.theme = "Adventure";
        this.settings = "A magical forest";
    }
}
