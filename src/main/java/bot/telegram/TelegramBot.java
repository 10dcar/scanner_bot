package bot.telegram;



public class TelegramBot {
    private long chatId;
    private boolean localContentTest;
    private JsonReader jsonBots;

    public TelegramBot(boolean localContentTest){
        this.localContentTest = localContentTest;
        // read all the interrogation data
        this.jsonBots = new JsonReader();
    }

    public String getBotUsername() {
        String botName = this.jsonBots.getObjReaded().botInfo().getTelegramBotName();
        return botName;
    }

    public String getBotToken() {
        String botToken = this.jsonBots.getObjReaded().botInfo().getTelegramBotToken();
        System.out.println("Bot token::::::" + botToken);
        return botToken;
    }

    public long getChatId() {
        return this.chatId;
    }

    public String onUpdateReceived(long chatId, String text) {
        boolean timerUpdate = false;
        String score = this.getScoreAll(timerUpdate);
        this.chatId = chatId;

        System.out.println("UpdateReceived::::::" + text);

        //System.out.println(score);
        return score;
    }

    public String getScoreAll(boolean timerUpdate){
        String scoreForta = this.jsonBots.getObjReaded().interrogateScanner(this.localContentTest, timerUpdate);
        String scoreStorj = this.jsonBots.getObjReaded().interrogateNode(this.localContentTest, timerUpdate);

        return scoreForta+""+scoreStorj;
    }
}
