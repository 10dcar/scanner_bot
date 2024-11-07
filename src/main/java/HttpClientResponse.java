import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    public Integer getScore(){
        //looking to get score and value
        try {
            String scJs = "lowestScores";

            //Object obj = new JSONParser().parse(this.body);
            Object obj = new JSONParser().parse(new FileReader(jsonPathName));
            this.jo = (JSONObject) obj;

            (String)this.jo.get(scJs);

            Iterator<Map.Entry> itr1;
            Iterator itr2 = ja.iterator();

            System.out.println("---------Citit JSON adrese: ");
            while (itr2.hasNext()) {
                itr1 = ((Map) itr2.next()).entrySet().iterator();

                while (itr1.hasNext()) {
                    if(pair.getKey() == "score"){
                        return pair.getValue();
                    }
                }
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
    public Integer getResponseStatus(){
        return this.statusCode;
    }
}
