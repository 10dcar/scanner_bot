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

    @Override
    public String getBotUsername() {
        JsonReader json = new JsonReader();
        ArrayList<TelegramBotData> jsonRedArr;
        try {
            jsonRedArr = json.readBot();

            return jsonRedArr.get(0).getBotName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getBotToken() {
        JsonReader json = new JsonReader();
        ArrayList<TelegramBotData> jsonRedArr;

        try {
            jsonRedArr = json.readBot();
            botToken = jsonRedArr.get(0).getBotToken();
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
        ArrayList<ScoreApi> jsonScoreArr;
        ArrayList<HttpClientData> jsonScannerArr;

        String scoreApi, scannerAddress;

        jsonScoreArr = json.readScoreApi();
        scoreApi = jsonScoreArr.get(0).getApiUrl();

        jsonScannerArr = json.readClient();
        scannerAddress = jsonScannerArr.get(3).getClientAddress();
        HttpClientResponse hcr = hcl.interrogate(scoreApi, scannerAddress);

        return hcr.getScore();
    }

    public void send(String messageText){
        SendMessage message = SendMessage.builder()
                .chatId(this.chatId+"")
                .text("You said: " + messageText).build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
