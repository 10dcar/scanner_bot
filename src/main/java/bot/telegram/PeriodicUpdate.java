package bot.telegram;

import java.util.Timer;
import java.util.TimerTask;

public class PeriodicUpdate {
    TelegramBot bot;
    Boolean local;

    public PeriodicUpdate(TelegramBot bot, Boolean local) {
        this.bot = bot;
        this.local = local;
    }

    public void periodicUpdate(){
        Timer timer = new Timer();
        TimerTask task = new TimerUpdate(this.bot, this.local);

        timer.schedule(task, 10000, 50000);
    }
}
