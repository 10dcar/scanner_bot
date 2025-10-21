package bot.telegram;


import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) throws Exception {
        // Take the content from local file for test or online localContent = true/false
        boolean localContentTest = false;

        TelegramBot bot = new TelegramBot(localContentTest);
        PollingBot pollingBot = new PollingBot(bot);
        pollingBot.poll();

        // Periodic updates
        Timer timer = new Timer();
        TimerTask task = new TimerUpdate(bot, pollingBot);
        timer.schedule(task, 10000, 1000*60*5);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown Hook Running...");
            // Perform cleanup tasks here (e.g., closing files, releasing resources).

            // Nothing to cleanup (checked: HttpClient, HttpRequest, SSLContext, X509TrustManager, Files, File)
        }));
    }
}
