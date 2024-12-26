package bot.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TelegramBot extends TelegramLongPollingBot {
    long chatId;
    Boolean localContentTest;
    Bot bot;

    public TelegramBot(Bot bot, Boolean localContentTest){
        this.localContentTest = localContentTest;
        // read all the interrogation data
        this.bot = bot;
    }

    @Override
    public String getBotUsername() {
        return  this.bot.getName();
    }

    @Override
    public String getBotToken() {
        return this.bot.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String score = "Null";
        this.chatId = update.getMessage().getChatId();

        System.out.println("UpdateReceived::::::");
        System.out.println(update.getMessage().getText() + " " + this.bot.getUrl() + " " + this.bot.getAddress());

        System.out.println(this.getScore());
        this.send(this.getScore());
    }

    public String getScore() {
        HttpClientLocal hcl = new HttpClientLocal();
        HttpClientResponse rsp = hcl.interrogate(this.bot.getUrl(), this.bot.getAddress());

        return this.bot.getMessage() + rsp.getScore(this.localContentTest);
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
