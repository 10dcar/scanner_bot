package bot.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws Exception {
        // Take the content from local file for test or online localContent = true/false
        Boolean localContentTest = false;
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        JsonReader jsonBots = new JsonReader();

        for (FortaBot fortaBot : jsonBots.getForta()) {
            //System.out.println("main bot name: "+fortaBot.getAddress());
            TelegramBot bot = new TelegramBot(fortaBot, localContentTest);

            botsApi.registerBot(bot);
            PeriodicUpdate pa = new PeriodicUpdate(bot);
            pa.periodicUpdate();
            break;
        }
        for (StorjBot storjBot : jsonBots.getStorj()) {
            /*TelegramBot bot = new TelegramBot(storjBot, localContentTest);

            botsApi.registerBot(bot);*/
            /*PeriodicUpdate pa = new PeriodicUpdate(bot);
            pa.periodicUpdate();*/
        }
    }
}
