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
    JsonReader jsonBots;

    public TelegramBot(JsonReader jsonBots, Boolean localContentTest){
        this.localContentTest = localContentTest;
        // read all the interrogation data
        this.jsonBots = jsonBots;
    }

    @Override
    public String getBotUsername() {
        String botNames = null;
        for (FortaBot fortaBot : this.jsonBots.getForta()) {
            botNames += "Name: " + fortaBot.getName() + "\n";
        }
        for (StorjBot storjBot : this.jsonBots.getStorj()) {
            botNames += "Name: " + storjBot.getName() + "\n";
        }
        return botNames;
    }

    @Override
    public String getBotToken() {
        for (FortaBot fortaBot : this.jsonBots.getForta()) {
            return fortaBot.getToken();
        }
        return null;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String score = "Null";
        this.chatId = update.getMessage().getChatId();

        System.out.println("UpdateReceived::::::" + update.getMessage().getText());

        System.out.println(this.getScore());
        this.send(this.getScore());
    }

    public String getScore() {
        String scores = null;
        for (FortaBot fortaBot : this.jsonBots.getForta()) {
            System.out.println(fortaBot.getUrl() + " " + fortaBot.getAddress());
            HttpClientLocal hcl = new HttpClientLocal();
            HttpClientResponse rsp = hcl.interrogate(fortaBot.getUrl()+fortaBot.getAddress());
            scores += "Name: " + fortaBot.getName() + " " + fortaBot.getMessage() + rsp.getScore(this.localContentTest) + "\n";
        }
        for (StorjBot storjBot : this.jsonBots.getStorj()) {
            System.out.println(storjBot.getUrl() + " " + storjBot.getAddress());
            HttpClientLocal hcl = new HttpClientLocal();
            HttpClientResponse rsp = hcl.interrogate(storjBot.getUrl()+":"+storjBot.getAddress());
            scores += "Name: " + storjBot.getName() + " " + storjBot.getMessage() + rsp.getScore(this.localContentTest) + "\n";
        }

        return scores;
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
