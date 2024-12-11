package bot.telegram;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class JsonReader {
    JSONObject jo;

    public JsonReader() {
        String jsonPathName;

        jsonPathName = "./target/dependency/config.json";

        try {
            Object obj = new JSONParser().parse(new FileReader(jsonPathName));
            this.jo = (JSONObject) obj;
        } catch(IOException ioe){

        } catch(ParseException pe){

        }
    }

    public TelegramBotData readBot() {
        String tbn = "telegram_bot_name";
        String tbt = "telegram_bot_token";
        ArrayList<TelegramBotData> ret = new ArrayList<>();
        TelegramBotData tbd = null;

        if(this.jo != null) {
            ret.add(new TelegramBotData((String) this.jo.get(tbn), (String) this.jo.get(tbt)));

            ret.forEach((s) ->
                    System.out.println("key: " + s.getBotName() + " value " + s.getBotToken()));
        }

        try {
            tbd = ret.get(0);
        } catch (IndexOutOfBoundsException e) { };

        return tbd;
    }

    public String readScoreApi() {
        String sau = "score_api_url";
        ArrayList<String> ret = new ArrayList<>();

        ret.add((String) this.jo.get(sau));

        return ret.get(0);
    }

    public ArrayList<HttpClientData> readClient(String client) {
        ArrayList<HttpClientData> ret = new ArrayList<>();

        JSONArray ja = (JSONArray) this.jo.get(client);
        if(ja != null) {
            Iterator<Map.Entry> itr1;
            Iterator itr2 = ja.iterator();

            while (itr2.hasNext()) {
                itr1 = ((Map) itr2.next()).entrySet().iterator();

                if (itr1.hasNext()) {
                    Map.Entry pair = itr1.next();
                    System.out.println(pair.getKey() + " : " + pair.getValue());

                    ret.add(new HttpClientData((String) pair.getKey(), (String) pair.getValue()));
                }
            }
        }
        return ret;
    }
}
