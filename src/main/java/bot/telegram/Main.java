package bot.telegram;

import com.google.common.base.Strings;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws Exception {
        // Take the content from local file for test or online localContent = true/false
        Boolean localContentTest = false;
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        TelegramBot bot = new TelegramBot(localContentTest);

        //try{
            botsApi.registerBot(bot);

            PeriodicUpdate pa = new PeriodicUpdate(bot);
            pa.periodicUpdate();
        //} catch(TelegramApiException e){ }
    }
}
