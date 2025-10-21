package bot.telegram;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PollingBot {
    private static TelegramBot telegramBot;
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    PollingBot (TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }
    // regex to extract update_id, chat id and text (simple and tolerant)
    private static final Pattern UPDATE_PATTERN = Pattern.compile(
            "\"update_id\"\\s*:\\s*(\\d+).*?\"chat\"\\s*:\\s*\\{[^}]*\"id\"\\s*:\\s*(-?\\d+)[^}]*\\}.*?(?:\"text\"\\s*:\\s*\"(.*?)\")?",
            Pattern.DOTALL);

    public void poll() throws IOException, InterruptedException {
        long offset = 0;
        String botToken = telegramBot.getBotToken();

        System.out.println("Starting polling...");

        while (true) {
            System.out.println("Starting polling next...");
            try {

                String getUpdatesUrl = "https://api.telegram.org/bot" + botToken + "/getUpdates?timeout=30&allowed_updates=true"
                        + (offset > 0 ? "&offset=" + offset : "");
                HttpRequest req = HttpRequest.newBuilder()
                        .uri(URI.create(getUpdatesUrl))
                        .timeout(Duration.ofSeconds(40))
                        .GET()
                        .build();

                HttpResponse<String> resp = CLIENT.send(req, HttpResponse.BodyHandlers.ofString());
                String body = resp.body();
                if (body == null || body.isBlank()) {
                    continue;
                }

                Matcher m = UPDATE_PATTERN.matcher(body);
                long maxSeen = offset;
                while (m.find()) {
                    long updateId = Long.parseLong(m.group(1));
                    long chatId = Long.parseLong(m.group(2));
                    String text = m.groupCount() >= 3 ? m.group(3) : null;
                    if (text == null) text = "";

                    //System.out.println("Got update_id=" + updateId + " chatId=" + chatId + " text=" + text);

                    // Example reply: echo incoming text
                    String reply = " " + telegramBot.onUpdateReceived(chatId, text);
                    boolean sent = sendMessage(botToken, chatId, reply);
                    //System.out.println("sendMessage sent=" + sent);

                    if (updateId >= maxSeen) maxSeen = updateId;
                }

                // advance offset so Telegram won't resend processed updates
                if (maxSeen > 0) offset = maxSeen + 1;

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) { }
            }
        }
    }

    public static boolean sendMessage(String botToken, long chatId, String text) throws IOException, InterruptedException {
        String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";
        String json = "{\"chat_id\":" + chatId + ",\"text\":\"" + escapeJson(text) + "\"}";
        System.out.println("Sending message to chat_id=" + chatId + ": " + text);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> resp = CLIENT.send(req, HttpResponse.BodyHandlers.ofString());
        return resp.statusCode() == 200 && resp.body().contains("\"ok\":true");
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }
}