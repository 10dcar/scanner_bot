package bot.telegram;

import java.util.TimerTask;

public class TimerUpdate extends TimerTask {
    TelegramBot bot;

    public TimerUpdate(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        Boolean timerUpdate = true;
        try {
            String scores = this.bot.getScoreAll(timerUpdate);

            System.out.println("!!!!!Timed update (" + java.time.LocalDateTime.now() + ")");
            if(!"".equals(scores)) {
                this.bot.send(scores);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
