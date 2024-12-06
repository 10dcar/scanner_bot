package bot.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws Exception {
        Boolean local = true;
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        TelegramBot bot = new TelegramBot(local);

        botsApi.registerBot(bot);
        PeriodicUpdate pa = new PeriodicUpdate(bot, local);
        pa.periodicUpdate();
    }
}
