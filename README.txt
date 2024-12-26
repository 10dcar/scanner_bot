HOW IT WORKS
//cum functioneaza:
/*
pornesc programul si astept mesajul din telegram (receptionez cu TelegramBot)
primesc mesajul din telegram inregistrez NUMARUL cu care sunt apelat (in TelegramBot)
trimit raspuns inapoi cu rezultatul interogarii folosind NUMARUL de mai sus
* */

TO DO
jsonPathName = "/app/dependency/config.json";

+COPY --from=build /app/target/classes /app/classes
 COPY --from=build /app/target/dependency /app/dependency
-COPY --from=build /app/target/classes /app/dependency
-COPY --from=build /app/src/main/resources/config.json /app/target/dependency
+COPY --from=build /app/src/main/resources /app/resources

 jsonPathName = "./target/dependency/config.json";
+        //jsonPathName = "/app/resources/config.json";
+        //jsonPathName = "./src/main/resources/config.json";
+        //jsonPathName = "./target/classes/config.json";
+        jsonPathName = "/app/dependency/config.json";

//diferente intre clase si interfete:
0. atat clasele cat si interfetele pot sa contina metode cu tot cu implementare
1. putem mosteni o singura clasa dar oricate interfete
2. variabilele in interfata nu exista variable (exista doar constante) spre deosebite de clase unde poti sa ai orice fel
de variabile
C++ permite sa mostenesti mai multe clase si problema este de variabile: daca au amandoua aceeasi variabila
Java permite mostenirea multipla dar cu ajutorul interfeteor nu cu ajutorul claselor

//odata cu suportul de metode cu tot cu implementare in interfete aparut in java 8
//a aparut si suportul pentru metode anonime care sn si lambde (exemple de utilizare)
//in toate limbajele de programare putem avea doua tipuri de relatii intre clase:
1. is-a relationship (mostenire) exemplu: masina-chevrolet (ales in situatii exceptionale)
2. has-a relationship (agregare sau compozitie) exemplu: masina-roti (de ales preferabil)


//de verificat daca merge fara copy urile din Dockerfile
//COPY --from=build /app/target/dependency /app/dependency
//COPY --from=build /app/target/classes /app/dependency
//add git CLONE to Dockerfile

//statistici server utilisation:
//1 rulezi docker run cu anumiti parametri care porneste o instanta de node-exporter
//2 confiurare si pornire a unui alt docker, tot cu docker run (dar si de configurat), un prometheus care colecteaza datele produse
//la pasul 1
//3 configurarea si pornirea unui docker cu docker run numit graphana (afiseaza intr o interfata grafica informatiile de la pasul 2)

//statistici storj

//casturi si algoritmi interzise
//termin programul care cisteste scorul si infrumusetez codul (renunt la citirile de la tastatura care sunt inutile)

//de revizuit (si adus in cea mai frumoasa stare) si de facut un pull request
/////////////////////////////////

//daca scorul este prea mic sa trimita mesaj pe telegram
//sa am pe telegram diferite comenzi pe care sa le pot da
//++++++
//de facut ceva pt storj (centralizat statistici despre toate nodurile intr un nod)
//storj monitorizare si alertare

////////////////////////////////////////////////////////////////////////////
//trece prin fiecare element al array ului
//face interogarea
//daca scorul este prea mic sa trimita mesaj pe telegram
//sa am pe telegram diferite comenzi pe care sa le pot da
//getAllScannerScores()
//sendScannerScoreAlert() {//dc am mai multe sa zica cum il cheama}
//atentie la structuri de date, clase si metode bine aranjate

//cum porneste serverul (daca dau restart la server daca mai primesc notificari)

//alchemy, dupa ora 16
//de incercat ankr daca nu merge
//++++++

////////////////////////////////////////////////////