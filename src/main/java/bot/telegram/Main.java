package bot.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws Exception {
        // Take the content from local file for test or online localContent = true/false
        Boolean localContentTest = false;
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        JsonReader jsonBots = new JsonReader();

        TelegramBot bot = new TelegramBot(jsonBots, localContentTest);
        botsApi.registerBot(bot);
        PeriodicUpdate pa = new PeriodicUpdate(bot);
        pa.periodicUpdate();
    }
}
