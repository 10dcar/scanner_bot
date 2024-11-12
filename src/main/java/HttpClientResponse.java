import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class HttpClientResponse {
    Integer statusCode;
    List<String> headers;
    String body;
    static String jsonPathName = "./src/main/resources/scannertest.json";
    JSONObject jo;

    public HttpClientResponse(Integer statusCode, List<String> headers, String body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;

        System.out.println("Status code: " + statusCode);
        System.out.println("Headers: " + headers);
        System.out.println("Body: " + body);
    }

    public String getScore(){
        //looking to get score and value
        try {
            String scJsParent = "lowestScores";
            String scJsChild = "score";
            String score;

            //Object obj = new JSONParser().parse(this.body);
            String content = new String(Files.readAllBytes(Paths.get(jsonPathName)));
            JSONObject jsonObject = new JSONObject(content);

            JSONArray nestedJsonArray = jsonObject.getJSONArray(scJsParent);
            JSONObject nestedJsonObject = nestedJsonArray.getJSONObject(0);
            String nestedValue = String.valueOf(nestedJsonObject.getNumber(scJsChild));

            System.out.println(nestedValue);

            return nestedValue;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Integer getResponseStatus(){
        return this.statusCode;
    }
}
