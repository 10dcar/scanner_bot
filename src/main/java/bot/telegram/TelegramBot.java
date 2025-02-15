package bot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TelegramBot extends TelegramLongPollingBot {
    private long chatId;
    private Boolean localContentTest;
    private JsonReader jsonBots;

    public TelegramBot(Boolean localContentTest){
        this.localContentTest = localContentTest;
        // read all the interrogation data
        this.jsonBots = new JsonReader();
    }

    @Override
    public String getBotUsername() {
        String botName = this.jsonBots.getObjReaded().botInfo().getTelegramBotName();
        System.out.println("Bot name::"+botName);
        return botName;
    }

    @Override
    public String getBotToken() {
        String botToken = this.jsonBots.getObjReaded().botInfo().getTelegramBotToken();
        System.out.println("Bot token::"+botToken);
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String score = this.getScoreAll();
        this.chatId = update.getMessage().getChatId();

        System.out.println("UpdateReceived::::::" + update.getMessage().getText());

        System.out.println(score);
        this.send(score);
    }

    public String getScoreAll(){
        String scoreForta = this.jsonBots.getObjReaded().interrogateScanner();
        String scoreStorj = this.jsonBots.getObjReaded().interrogateNode();

        return scoreForta+" "+scoreStorj;
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
