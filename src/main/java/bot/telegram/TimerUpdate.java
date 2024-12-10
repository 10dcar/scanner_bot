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

            System.out.println("Timed update score: "+score);

            if((Float.compare(Float.parseFloat(score), 0f) > 0) && (Float.compare(Float.parseFloat(score), 0.5f) < 0)) {
                this.bot.send(score);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
