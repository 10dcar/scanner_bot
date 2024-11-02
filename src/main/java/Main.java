/*import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new FortaLinuxJavaScannerBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}*/

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        FortaLinuxJavaScannerBot bot = new FortaLinuxJavaScannerBot();
        botsApi.registerBot(bot);

        //cum functioneaza:
        /*
        pornesc programul si astept mesajul din telegram
        primesc mesajul din telegram inregistrez NUMARUL cu care sunt apelat
        trimit raspuns inapoi cu rezultatul interogarii folosind NUMARUL de mai sus
        * */
        Scanner s = new Scanner(System.in);
        s.nextLine();
        bot.saySomething();
        s.close();
    }
}
//de revizuit (si adus in cea mai frumoasa stare) si de facut un pull request

//de pus programul java sa ruleze intr un container de docker non stop

//daca scorul este prea mic sa trimita mesaj pe telegram
//sa am pe telegram diferite comenzi pe care sa le pot da
//getAllScannerScores()
//sendScannerScoreAlert() {//dc am mai multe sa zica cum il cheama}
//atentie la structuri de date, clase si metode bine aranjate
//++++++
//de facut ceva pt storj
//o reteta de docker prin care programul de java sa ruleze in docker automat si dupa restart(docker fiind o alternativa la systemd)

////////////////////////////////////////////////////////////////////////////
//trece prin fiecare element al array ului
//face interogarea
//daca scorul este prea mic sa trimita mesaj pe telegram
//sa am pe telegram diferite comenzi pe care sa le pot da
//getAllScannerScores()
//sendScannerScoreAlert() {//dc am mai multe sa zica cum il cheama}
//atentie la structuri de date, clase si metode bine aranjate

//cum porneste serverul (daca dau restart la server daca mai primesc notificari)
//aplicatia asta pe server (de cautat care este cea mai uzuala metoda ca sa repornesc procesele la statup fara docker apoi cu docker)
//
//cum sa lucrezi cu systemd
//de configurat sa fie responsabil de forta
//systemctl status forta
//systemctl restart forta
//
//alchemy, dupa ora 16
//de incercat ankr daca nu merge
//++++++
//de facut ceva pt storj
//o reteta de docker prin care programul de java sa ruleze in docker automat si dupa restart(docker fiind o alternativa la systemd)

////////////////////////////////////////////////////