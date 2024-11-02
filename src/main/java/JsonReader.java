
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class JsonReader {
    static String jsonPathName = "./src/main/resources/config.json";

    public ArrayList<> read(String option) throws Exception {
        Object obj = new JSONParser().parse(new FileReader(jsonPathName));
        JSONObject jo = (JSONObject) obj;

        switch (option) {
            case "bot_data":
                String tbn = "telegram_bot_name";
                String tbt = "telegram_bot_token";
                String sau = "score_api_url";
                ArrayList<TelegramBotData> ret = new ArrayList<>();

                ret.add(new TelegramBotData((String)jo.get(tbn), (String)jo.get(tbt), (String)jo.get(sau)));

                ret.forEach((s) ->
                        System.out.println("key: " + s.getBotName() + " value " + s.getBotToken()));
                //System.out.println("---------Citit JSON bot data: " + botName + " " + botToken + " " + apiUrl);

                break;
            case "http_client_data":
                ArrayList<HttpClientData> ret = new ArrayList<>();

                JSONArray ja = (JSONArray) jo.get("polygon_scanner_address");
                Iterator<Map.Entry> itr1;
                Iterator itr2 = ja.iterator();

                System.out.println("---------Citit JSON adrese: ");
                while (itr2.hasNext()) {
                    itr1 = ((Map) itr2.next()).entrySet().iterator();

                    while (itr1.hasNext()) {
                        Map.Entry pair = itr1.next();
                        //System.out.println(pair.getKey() + " : " + pair.getValue());

                        ret.add(new HttpClientData((String)pair.getKey(), (String)pair.getValue()));
                        break;
                    }

                }
                break;
        }

        return ret;
    }
}
