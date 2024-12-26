package bot.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TelegramBot extends TelegramLongPollingBot {
    long chatId;
    String botToken;
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

        /*TelegramBotData jsonRed;

        try {
            jsonRed = this.json.readBot();

            return jsonRed.getBotName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;*/
    }

    @Override
    public String getBotToken() {
        return this.bot.getToken();

        /*for (StorjBot storjBot : this.jsonBot.getStorj()) {
            storjBot.getAddress();
        }*/
        /*TelegramBotData jsonRed;

        try {
            jsonRed = this.json.readBot();
            if(jsonRed != null) {
                botToken = jsonRed.getBotToken();
                return botToken;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;*/
    }

    @Override
    public void onUpdateReceived(Update update) {
        String score = "Null";
        this.chatId = update.getMessage().getChatId();

        System.out.println(update.getMessage().getText() + " " + this.bot.getUrl() + " " + this.bot.getAddress());
        HttpClientLocal hcl = new HttpClientLocal();
        HttpClientResponse rsp = hcl.interrogate(this.bot.getUrl(), this.bot.getAddress());
        score = rsp.getScore(this.localContentTest);

        this.send(this.bot.getMessage() + score + " ");
        /*try {
            //https://api.forta.network/stats/sla/scanner/0x2b1c74aaed16b60833aa1d2e0776b8be53bbb6d8
            score = this.getScore("forta_scanner_address");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.send("Scorul actual Forta este: " + score + " ");
        try {
            score = this.getScore("storj_scanner_address_srv1");
            //score = this.getScore("storj_scanner_address_srv2");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

        //this.send("Scorul actual Storj este: " + score + " ");
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
