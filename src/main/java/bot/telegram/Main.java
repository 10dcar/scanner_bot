// java
package bot.telegram;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean localContentTest = false;

        TelegramBot bot = new TelegramBot(localContentTest);
        PollingBot pollingBot = new PollingBot(bot);

        // Start polling in a background thread so main can continue to schedule the TimerTask.
        Thread pollingThread = new Thread(() -> {
            try {
                pollingBot.poll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "PollingBot-Thread");
        pollingThread.start();

        // Periodic updates
        Timer timer = new Timer("TimerUpdate");
        TimerTask task = new TimerUpdate(bot, pollingBot);
        timer.schedule(task, 10_000, 1000 * 60 * 60);

        System.out.println("Bot started. Press Ctrl+C to stop.");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown Hook Running...");
            timer.cancel();
            pollingThread.interrupt();
        }));
        System.out.println("Main finished.");
        pollingThread.join();
    }
}
