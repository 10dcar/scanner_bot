package bot.telegram;

import org.json.JSONArray;
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
    JSONObject jo;

    public HttpClientResponse(Integer statusCode, List<String> headers, String body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;

        System.out.println("Status code: " + statusCode);
        System.out.println("Headers: " + headers);
        System.out.println("Body: " + body);
    }

    public String getScoreValue(Boolean local){
        String jsonPathName;
        String content;

        if(local) {
            jsonPathName = "./src/main/resources/scannertest.json";
            try {
                content = new String(Files.readAllBytes(Paths.get(jsonPathName)));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            jsonPathName = "/app/resources/scannertest.json";
            content = this.getResponseBody();
        }
        return content;
    }
    public String getScore(Boolean local){
        //looking to get score and value
        String scJsParent = "lowestScores";
        String scJsChild = "score";

        JSONObject jsonObject = new JSONObject(this.getScoreValue(local));

        JSONArray nestedJsonArray = jsonObject.getJSONArray(scJsParent);
        JSONObject nestedJsonObject = nestedJsonArray.getJSONObject(0);
        String nestedValue = String.valueOf(nestedJsonObject.getNumber(scJsChild));

        System.out.println(nestedValue);

        return nestedValue;
    }

    public Integer getResponseStatus(){
        return this.statusCode;
    }
    public String getResponseBody(){
        return this.body;
    }
}
