package bot.telegram;

import org.json.JSONObject;

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
        //looking to get score and value
        String scJsParent = "statistics";
        String scJsChild = "avg";
        String nestedValue = "";

        try {
            JSONObject jsonObject = new JSONObject(this.getScoreValue(localContentTest));

            JSONObject nestedJsonObject = jsonObject.getJSONObject(scJsParent);
            if (nestedJsonObject != null) {
                nestedValue = String.valueOf(nestedJsonObject.getNumber(scJsChild));
            }
        } catch(Exception e){
            nestedValue = this.body;
        }

        return nestedValue;
    }
}
