package bot.telegram;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BotInfo {
    private String telegramBotName;
    private String telegramBotToken;

    @JsonCreator
    public BotInfo(
            @JsonProperty("telegram_bot_name") String telegramBotName,
            @JsonProperty("telegram_bot_token") String telegramBotToken
    ) {
        this.telegramBotName = telegramBotName;
        this.telegramBotToken = telegramBotToken;
    }

    public String getTelegramBotName() {
        return telegramBotName;
    }

    public String getTelegramBotToken() {
        return telegramBotToken;
    }

    @Override
    public String toString() {
        return "BotInfo{" +
                "telegramBotName='" + telegramBotName + '\'' +
                ", telegramBotToken='" + telegramBotToken + '\'' +
                '}';
    }
}
