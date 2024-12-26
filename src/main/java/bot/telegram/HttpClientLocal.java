package bot.telegram;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientLocal {
    public HttpClientResponse interrogate(String scoreApi, String scannerAddress) {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(scoreApi+scannerAddress))
                    .build();
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
            //System.out.println("scoreApi: " + scoreApi + " scannerAddress: "+scannerAddress);

            return new HttpClientResponse(response.statusCode(),
                    response.headers().allValues("content-type"),
                    response.body());
        } catch (ConnectException e) {
            e.printStackTrace();
            // Handle ConnectException
        } catch (java.io.IOException e) {
            e.printStackTrace();
            // Handle IOException
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Handle InterruptedException
        }

        return null;
    }
}
