import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;

public class FortaLinuxJavaScannerBot extends TelegramLongPollingBot {
    long chatId;
    String botToken;

    @Override
    public String getBotUsername() {
        JsonReader json = new JsonReader();
        ArrayList<TelegramBotData> jsonRedArr;
        System.out.println("+++++++getBotUsername");
        try {
            jsonRedArr = json.readBot();
            //jsonRedArr.forEach((s) ->System.out.println("key: " + s.getBotName() + " value " + s.getBotToken()));

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

        System.out.println("+++++++getBotToken");
        try {
            jsonRedArr = json.readBot();
            botToken = jsonRedArr.get(0).getBotToken();
            System.out.println("+++++++token::::" + botToken);
            return botToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onUpdateReceived(Update update) {
        this.chatId = update.getMessage().getChatId();
        String messageText = "Am primit mesaj de la BOT (receptionat ID ul)";
        System.out.println(update.getMessage().getText());

        this.send(messageText + " Ii confirm cu acest mesaj ca am primit. ");
    }

    public void saySomething() throws Exception {
        HttpClientLocal hcl = new HttpClientLocal();
        JsonReader json = new JsonReader();
        ArrayList<ScoreApi> jsonScoreArr;
        ArrayList<HttpClientData> jsonScannerArr;

        System.out.println("++++++++Am spus ceva");
        //citest datele despre
        String scoreApi, scannerAddress;

        jsonScoreArr = json.readScoreApi();
        scoreApi = jsonScoreArr.get(0).getApiUrl();

        jsonScannerArr = json.readClient();
        scannerAddress = jsonScannerArr.get(2).getClientAddress();

        //parul urmator: trebuie sa scot scorul din acest mesaj
        this.send(hcl.interrogate(scoreApi, scannerAddress));
    }

    public void send(String messageText){
        SendMessage message = SendMessage.builder()
                .chatId(this.chatId+"") //Who are we sending a message to
                .text("You said: " + messageText).build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
