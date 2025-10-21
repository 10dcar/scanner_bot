package bot.telegram;

import java.util.TimerTask;

public class TimerUpdate extends TimerTask {
    TelegramBot bot;
    PollingBot pollingBot;

    public TimerUpdate(TelegramBot bot, PollingBot pollingBot) {
        this.bot = bot;
        this.pollingBot = pollingBot;
    }

    @Override
    public void run() {
        boolean timerUpdate = true;
        try {
            String scores = this.bot.getScoreAll(timerUpdate);

            System.out.println("!!!!!Timed update (" + java.time.LocalDateTime.now() + ")");
            if(!"".equals(scores)) {
                this.pollingBot.sendMessage(bot.getBotToken(), bot.getChatId(), scores);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
