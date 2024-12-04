package bot.telegram;

import java.util.TimerTask;

public class TimerUpdate extends TimerTask {
    TelegramBot bot;

    public TimerUpdate(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        try {
            this.bot.send(this.bot.getScore());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
