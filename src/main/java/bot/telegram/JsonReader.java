package bot.telegram;

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

        jsonPathName = "./src/main/resources/config.json";

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
    }

    public Boolean readFortaBots(BotDataDefinition fortaStorj) {
        this.forta = new ArrayList<>();
        for (Map.Entry<String, ScannerDetails> entry : fortaStorj.getForta().entrySet()) {
            //System.out.println(entry.getValue().getScoreApiUrl() + " " + entry.getValue().getTelegramBotToken());
            for (ScannerAddress addressData : entry.getValue().getFortaScannerAddress()) {
                //System.out.println(addressData.getName()+" "+addressData.getAddress());
                forta.add(new FortaBot(addressData.getName(), addressData.getAddress(), entry.getValue().getScoreApiUrl(), entry.getValue().getTelegramBotToken()));
            }
        }
        return true;
    }

    public Boolean readStorjBots(BotDataDefinition fortaStorj) {
        this.storj = new ArrayList<>();
        for (Map.Entry<String, ScannerDetails> entry : fortaStorj.getStorj().entrySet()) {
            //System.out.println(entry.getValue().getScoreApiUrl());
            for (ScannerAddress addressData : entry.getValue().getStorjScannerAddress()) {
                //System.out.println(addressData.getName()+" "+addressData.getAddress());
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
}
