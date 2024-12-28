package bot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class BotDataDefinition {
    @JsonProperty("forta")
    private Map<String, ScannerDetails> forta;
    @JsonProperty("storj")
    private Map<String, ScannerDetails> storj;

    public BotDataDefinition() { }

    // Getters și Setters
    public Map<String, bot.telegram.ScannerDetails> getForta() {
        return forta;
    }
    public void setForta(Map<String, bot.telegram.ScannerDetails> forta) {
        this.forta = forta;
    }
    public Map<String, bot.telegram.ScannerDetails> getStorj() {
        return storj;
    }
    public void setStorj(Map<String, bot.telegram.ScannerDetails> storj) {
        this.storj = storj;
    }
}
