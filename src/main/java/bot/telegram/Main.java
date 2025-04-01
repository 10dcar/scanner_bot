package bot.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) throws Exception {
        // Take the content from local file for test or online localContent = true/false
        boolean localContentTest = false;
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        TelegramBot bot = new TelegramBot(localContentTest);

        botsApi.registerBot(bot);

        // Periodic updates
        Timer timer = new Timer();
        TimerTask task = new TimerUpdate(bot);
        timer.schedule(task, 10000, 1000*60*5);
    }
}
