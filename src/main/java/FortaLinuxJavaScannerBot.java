import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class FortaLinuxJavaScannerBot extends TelegramLongPollingBot {
    long chatId;
    String botToken;

    @Override
    public String getBotUsername() {
        JsonReader json = new JsonReader();
        String jsonRedArr[];
        System.out.println("+++++++getBotUsername");
        try {
            jsonRedArr = json.read("bot_data");
            System.out.println("nume:::"+ jsonRedArr[0] + " " + jsonRedArr[1]);

            return jsonRedArr[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getBotToken() {
        JsonReader json = new JsonReader();
        String jsonRedArr[];

        System.out.println("+++++++getBotToken");
        try {
            jsonRedArr = json.read("bot_data");
            botToken = jsonRedArr[1];
            System.out.println("+++++++token::::" + botToken);
            return botToken;
        } catch (IOException e) {
            e.printStackTrace();
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
        String jsonRedArr[];

        System.out.println("++++++++Am spus ceva");
        //citest datele despre
        String scoreApi, scannerAddress;

        jsonRedArr = json.read("bot_data");
        scoreApi = jsonRedArr[2];

        jsonRedArr = json.read("http_client_data");
        scannerAddress = jsonRedArr[1];

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
