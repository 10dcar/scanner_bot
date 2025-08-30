package bot.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
    private long chatId;
    private boolean localContentTest;
    private JsonReader jsonBots;

    public TelegramBot(boolean localContentTest){
        this.localContentTest = localContentTest;
        // read all the interrogation data
        this.jsonBots = new JsonReader();
    }

    @Override
    public String getBotUsername() {
        String botName = this.jsonBots.getObjReaded().botInfo().getTelegramBotName();
        return botName;
    }

    @Override
    public String getBotToken() {
        String botToken = this.jsonBots.getObjReaded().botInfo().getTelegramBotToken();
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        boolean timerUpdate = false;
        String score = this.getScoreAll(timerUpdate);
        this.chatId = update.getMessage().getChatId();

        System.out.println("UpdateReceived::::::" + update.getMessage().getText());

        System.out.println(score);
        this.send(score);
    }

    public String getScoreAll(boolean timerUpdate){
        String scoreForta = this.jsonBots.getObjReaded().interrogateScanner(this.localContentTest, timerUpdate);
        String scoreStorj = this.jsonBots.getObjReaded().interrogateNode(this.localContentTest, timerUpdate);

        return scoreForta+""+scoreStorj;
    }

    public void send(String messageText){
        if(this.chatId > 0) {
            SendMessage message = SendMessage.builder()
                    .chatId(this.chatId + "")
                    .text(messageText).build();
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
