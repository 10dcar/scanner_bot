package bot.telegram;

/*import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;*/

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonReader {
    List<FortaBot> forta;
    List<StorjBot> storj;

    public JsonReader() {
        String jsonPathName;

        //jsonPathName = "./dependency/config.json";
        jsonPathName = "./src/main/resources/config.json";
        //jsonPathName = "./target/dependency/config.json";
        //jsonPathName = "/app/dependency/config.json";

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            // Citim JSON-ul dintr-un fișier (sau un alt input stream)
            BotDataDefinition objFortaStorj = objectMapper.readValue(new File(jsonPathName), BotDataDefinition.class);

            // Accesăm datele din obiectul Java
            this.readFortaBots(objFortaStorj);
            this.readStorjBots(objFortaStorj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try {
            File file = new File(jsonPathName);
            if (file.exists()) {
                FileReader fr = new FileReader(file);

                JSONObject jsonObject = (JSONObject) new JSONParser().parse(fr);
                System.out.println("Object: " + jsonObject);
                this.joForta = (JSONObject) jsonObject.get("forta");
                this.joStorj = (JSONObject) jsonObject.get("storj");

                System.out.println("Bots: " + this.joForta + " " + this.joStorj);
            } else {
                System.out.println("File not found!");
            }
        } catch(IOException ioe){

        } catch(ParseException pe){

        }*/
    }

    public Boolean readFortaBots(BotDataDefinition fortaStorj) {
        this.forta = new ArrayList<>();
        for (Map.Entry<String, ScannerDetails> entry : fortaStorj.getForta().entrySet()) {
            System.out.println(entry.getValue().getScoreApiUrl() + " " + entry.getValue().getTelegramBotToken());
            for (ScannerAddress addressData : entry.getValue().getFortaScannerAddress()) {
                System.out.println(addressData.getName()+" "+addressData.getAddress());
                forta.add(new FortaBot(addressData.getName(), addressData.getAddress(), entry.getValue().getScoreApiUrl(), entry.getValue().getTelegramBotToken()));
            }
        }
        return true;
    }

    public Boolean readStorjBots(BotDataDefinition fortaStorj) {
        this.storj = new ArrayList<>();
        for (Map.Entry<String, ScannerDetails> entry : fortaStorj.getStorj().entrySet()) {
            System.out.println(entry.getValue().getScoreApiUrl());
            for (ScannerAddress addressData : entry.getValue().getStorjScannerAddress()) {
                System.out.println(addressData.getName()+" "+addressData.getAddress());
                storj.add(new StorjBot(addressData.getName(), addressData.getAddress(), entry.getValue().getScoreApiUrl(), entry.getValue().getTelegramBotToken()));
            }
        }
        return true;
    }

    public List<FortaBot> getForta() {
        return forta;
    }

    public void setForta(List<FortaBot> forta) {
        this.forta = forta;
    }

    public List<StorjBot> getStorj() {
        return storj;
    }

    public void setStorj(List<StorjBot> storj) {
        this.storj = storj;
    }
    /*public TelegramBotData readBot() {
        String tbn = "telegram_bot_name";
        String tbt = "telegram_bot_token";
        ArrayList<TelegramBotData> ret = new ArrayList<>();
        TelegramBotData tbd = null;

        ret.add(new TelegramBotData((String) this.joForta.get(tbn), (String) this.joForta.get(tbt)));

        ret.forEach((s) ->
                System.out.println("key: " + s.getBotName() + " value " + s.getBotToken()));

        try {
            tbd = ret.get(0);
        } catch (IndexOutOfBoundsException e) { };

        return tbd;
    }

    public String readScoreApi() {
        String sau = "score_api_url";
        ArrayList<String> ret = new ArrayList<>();

        ret.add((String) this.joForta.get(sau));

        return ret.get(0);
    }

    public ArrayList<HttpClientData> readAllClients() {
        return null;
    }

    public ArrayList<HttpClientData> readClient(String client) {
        ArrayList<HttpClientData> ret = new ArrayList<>();

        //1 aici creca numele clientului tre sa fie forta sau storj nu ce vine acum
        //2 si creca JSONReader() tre instantiat aici nu cand face obiectul TelegramBot()
        //3 si creca read client trebuia apelat inainte de readScore() ca sa nu il apelez de fiecare data
        //citesc o singura data clientul si cu datele pe care le citesc apelez de fiecare data getScore()
        //acu de fiecare data cand apelez getScore() citesc clientul
        JSONArray ja = (JSONArray) this.joForta.get(client);
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
    }*/
}
