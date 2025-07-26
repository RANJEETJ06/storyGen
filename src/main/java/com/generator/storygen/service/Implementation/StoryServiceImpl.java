package com.generator.storygen.service.Implementation;

import com.generator.storygen.service.StoryService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class StoryServiceImpl implements StoryService {

    @Value("${gemini.api-key}")
    private String apiKey;

    @Value("${gemini.endpoint}")
    private String endpoint; // Should be: https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String generateStory(String mainCharacter, String theme, String settings) {
        String prompt = String.format("""
            You are a creative story writer.
            Generate a story of 300–500 words.
            Main Character: %s
            Theme: %s
            Settings: %s
            Make the story engaging, vivid, and immersive.
            """, mainCharacter, theme, settings);

        // Gemini API request structure
        Map<String, Object> request = Map.of(
                "contents", List.of(Map.of(
                        "parts", List.of(Map.of("text", prompt))
                ))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        try {
            // Add API key as query param
            String urlWithKey = endpoint + "?key=" + apiKey;

            ResponseEntity<String> response = restTemplate.postForEntity(urlWithKey, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonObject json = JsonParser.parseString(response.getBody()).getAsJsonObject();
                JsonArray candidates = json.getAsJsonArray("candidates");

                if (candidates != null && candidates.size() > 0) {
                    JsonObject content = candidates.get(0).getAsJsonObject().getAsJsonObject("content");
                    JsonArray parts = content.getAsJsonArray("parts");
                    return parts.get(0).getAsJsonObject().get("text").getAsString();
                }
            }
        } catch (HttpClientErrorException.Unauthorized e) {
            return "Unauthorized: Please check your Gemini API key.";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Failed to generate story. Please try again.";
    }
}
