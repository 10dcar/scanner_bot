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
            String fortaScore = this.bot.getScore("forta_scanner_address");

            System.out.println("!!!!!Timed update Forta score: "+fortaScore + " ");
            try{
                if((Float.compare(Float.parseFloat(fortaScore), 0.8f) < 0)) {
                    this.bot.send(fortaScore);
                }
            } catch (NumberFormatException e) { }
            String storjScore = this.bot.getScore("storj_scanner_address_srv1");

            //foreach
            /*System.out.println("!!!!!Timed update Storj score: "+storjScore + " ");
            try{
                if((Float.compare(Float.parseFloat(storjScore), 0f) > 0) && (Float.compare(Float.parseFloat(storjScore), 0.5f) < 0)) {
                    this.bot.send(storjScore);
                }
            } catch (NumberFormatException e) { }*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
