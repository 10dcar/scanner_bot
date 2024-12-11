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

    public String getScoreValue(Boolean localContentTest){
        String jsonPathName;
        String content;

        //jsonPathName = "/app/resources/scannertest.json";
        //jsonPathName = "./src/main/resources/scannertest.json";
        //jsonPathName = "./target/classes/scannertest.json";
        jsonPathName = "/app/dependency/scannertest.json";

        if(localContentTest) {
            try {
                content = new String(Files.readAllBytes(Paths.get(jsonPathName)));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            content = this.getResponseBody();
        }
        return content;
    }
    public String getScore(Boolean localContentTest){
        //looking to get score and value
        String scJsParent = "lowestScores";
        String scJsChild = "score";
        String nestedValue = "";

        JSONObject jsonObject = new JSONObject(this.getScoreValue(localContentTest));

        JSONArray nestedJsonArray = jsonObject.getJSONArray(scJsParent);
        if(nestedJsonArray != null) {
            JSONObject nestedJsonObject = nestedJsonArray.getJSONObject(0);
            if(nestedJsonObject != null) {
                nestedValue = String.valueOf(nestedJsonObject.getNumber(scJsChild));
            }
        }

        return nestedValue;
    }

    public Integer getResponseStatus(){
        return this.statusCode;
    }
    public String getResponseBody(){
        return this.body;
    }
}
