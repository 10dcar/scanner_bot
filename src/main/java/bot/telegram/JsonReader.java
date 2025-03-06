package bot.telegram;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JsonReader {
    ObjectMapper objectMapper;
    BotDataDefinition objReaded;

    public JsonReader() {
        this.objectMapper = new ObjectMapper();

        try {
            objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            this.objReaded = objectMapper.readValue(new File(BotConstants.jsonPathName), BotDataDefinition.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BotDataDefinition getObjReaded() {
        return objReaded;
    }
}
