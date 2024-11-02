public class TelegramBotData {
    String telegram_bot_name;
    String telegram_bot_token;
    String score_api_url;

    public TelegramBotData(String telegram_bot_name, String telegram_bot_token, String score_api_url){
        this.telegram_bot_name = telegram_bot_name;
        this.telegram_bot_token = telegram_bot_token;
        this.score_api_url = score_api_url;
    }

    public String getBotName(){
        return this.telegram_bot_name;
    }
    public String getBotToken(){
        return this.telegram_bot_token;
    }
    public String getApiUrl(){
        return this.score_api_url;
    }
}
