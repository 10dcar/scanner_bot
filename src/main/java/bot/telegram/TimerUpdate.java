package bot.telegram;

import java.util.TimerTask;

public class TimerUpdate extends TimerTask {
    TelegramBot bot;
    Boolean local;

    public TimerUpdate(TelegramBot bot, Boolean local) {
        this.bot = bot;
        this.local = local;
    }

    @Override
    public void run() {
        try {
            String score = this.bot.getScore(this.local);
            if((Integer.parseInt(score) > 0) && (Integer.parseInt(score) < 5)) {
                this.bot.send(score);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
