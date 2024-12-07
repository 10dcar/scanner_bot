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

    public TelegramBot(Boolean localContentTest){
        this.localContentTest = localContentTest;
    }
    @Override
    public String getBotUsername() {
        JsonReader json = new JsonReader();
        TelegramBotData jsonRed;
        try {
            jsonRed = json.readBot();

            return jsonRed.getBotName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getBotToken() {
        JsonReader json = new JsonReader();
        TelegramBotData jsonRed;

        try {
            jsonRed = json.readBot();
            botToken = jsonRed.getBotToken();
            return botToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String score = "Null";
        this.chatId = update.getMessage().getChatId();

        System.out.println(update.getMessage().getText());
        try {
            score = this.getScore();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.send("Scorul actual este: " + score + " ");
    }

    public String getScore() throws Exception {
        HttpClientLocal hcl = new HttpClientLocal();
        JsonReader json = new JsonReader();
        ArrayList<HttpClientData> jsonScannerArr;
        String scannerAddress;

        jsonScannerArr = json.readClient();
        scannerAddress = jsonScannerArr.get(3).getClientAddress();
        HttpClientResponse hcr = hcl.interrogate(json.readScoreApi(), scannerAddress);

        return hcr.getScore(this.localContentTest);
    }

    public void send(String messageText){
        if(this.chatId > 0) {
            SendMessage message = SendMessage.builder()
                    .chatId(this.chatId + "")
                    .text("You said: " + messageText).build();
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
