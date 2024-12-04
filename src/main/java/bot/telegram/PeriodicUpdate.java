package bot.telegram;

import java.util.Timer;
import java.util.TimerTask;

public class PeriodicUpdate {
    TelegramBot bot;
    public PeriodicUpdate(TelegramBot bot) {
        this.bot = bot;
    }

    public void periodicUpdate(){
        Timer timer = new Timer();
        TimerTask task = new TimerUpdate(this.bot);

        timer.schedule(task, 10000, 50000);
    }
}
