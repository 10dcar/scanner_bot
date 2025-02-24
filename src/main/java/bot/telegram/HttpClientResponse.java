package bot.telegram;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class HttpClientResponse {
    Integer statusCode;
    List<String> headers;
    String body;

    public HttpClientResponse(Integer statusCode, List<String> headers, String body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public String getScoreValue(Boolean localContentTest){
        String content;

        if(localContentTest) {
            try {
                content = new String(Files.readAllBytes(Paths.get(BotConstants.jsonPathNameTest)));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                //problema e ca nu vad cum o arunc ca metoda nu are throws in definitie
                throw new RuntimeException(e);
            }
        }
        else {
            content = this.body;
        }
        return content;
    }
    public String getScore(Boolean localContentTest){
        String nestedValue = "";

        try {
            String jsonString = this.getScoreValue(localContentTest);
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                // Parse JSON string
                JsonNode rootNode = objectMapper.readTree(jsonString);

                if (rootNode.has("statistics")) {
                    // Navigate to statistics object
                    JsonNode statisticsNode = rootNode.path("statistics");

                    if (statisticsNode.has("avg")) {
                        // Get avg value
                        nestedValue = statisticsNode.path("avg").toString();
                    }
                } else if (rootNode.has("AllHealthy")) {
                    nestedValue = rootNode.get("AllHealthy").toString();
                }
            } catch (IOException e) {
                System.out.println("Client Response IO Exception");
            }
        } catch(Exception e){
            System.out.println("Client Response Exception in JSON body");
        }

        return nestedValue;
    }
}
