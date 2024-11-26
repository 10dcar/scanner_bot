package bot.telegram;

public class TelegramBotData {
    String telegram_bot_name;
    String telegram_bot_token;

    public TelegramBotData(String telegram_bot_name, String telegram_bot_token){
        this.telegram_bot_name = telegram_bot_name;
        this.telegram_bot_token = telegram_bot_token;
    }

    public String getBotName(){
        return this.telegram_bot_name;
    }
    public String getBotToken(){
        return this.telegram_bot_token;
    }
}
