package bot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

// Clasa pentru detaliile scannerului
public class ScannerDetails {
    @JsonProperty("telegram_bot_name")
    private String telegramBotName;

    @JsonProperty("telegram_bot_token")
    private String telegramBotToken;

    @JsonProperty("score_api_url")
    private String scoreApiUrl;

    @JsonProperty("forta_scanner_address")
    private List<bot.telegram.ScannerAddress> fortaScannerAddress;

    @JsonProperty("storj_scanner_address")
    private List<bot.telegram.ScannerAddress> storjScannerAddress;

    // Getters și Setters
    public String getTelegramBotName() {
        return telegramBotName;
    }

    public void setTelegramBotName(String telegramBotName) {
        this.telegramBotName = telegramBotName;
    }

    public String getTelegramBotToken() {
        return telegramBotToken;
    }

    public void setTelegramBotToken(String telegramBotToken) {
        this.telegramBotToken = telegramBotToken;
    }

    public String getScoreApiUrl() {
        return scoreApiUrl;
    }

    public void setScoreApiUrl(String scoreApiUrl) {
        this.scoreApiUrl = scoreApiUrl;
    }

    public List<bot.telegram.ScannerAddress> getFortaScannerAddress() {
        return fortaScannerAddress;
    }

    public void setFortaScannerAddress(List<bot.telegram.ScannerAddress> fortaScannerAddress) {
        this.fortaScannerAddress = fortaScannerAddress;
    }

    public List<bot.telegram.ScannerAddress> getStorjScannerAddress() {
        return storjScannerAddress;
    }

    public void setStorjScannerAddress(List<bot.telegram.ScannerAddress> storjScannerAddress) {
        this.storjScannerAddress = storjScannerAddress;
    }
}
