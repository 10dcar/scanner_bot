package bot.telegram;

import java.util.TimerTask;

public class

TimerUpdate extends TimerTask {
    TelegramBot bot;

    public TimerUpdate(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        try {
            String fortaScore = this.bot.getScoreAll();

            System.out.println("!!!!!Timed update (" + java.time.LocalDateTime.now() + ")");//: "+fortaScore + "
            try{
                if((Float.compare(Float.parseFloat(fortaScore), 0.8f) < 0)) {
                    this.bot.send(fortaScore);
                }
            } catch (NumberFormatException e) { }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
