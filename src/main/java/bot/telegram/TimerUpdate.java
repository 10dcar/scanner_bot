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
            String score = this.bot.getScore();
            if((Float.parseFloat(score) > 0) && (Float.parseFloat(score) < 5)) {
                this.bot.send(score);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
