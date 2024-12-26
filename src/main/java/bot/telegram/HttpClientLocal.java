package bot.telegram;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.net.http.HttpTimeoutException;
import java.util.concurrent.TimeoutException;

public class HttpClientLocal {
    public HttpClientResponse interrogate(String address) {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(address))
                    .build();
            HttpResponse<String> response = null;
            try {
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (HttpTimeoutException ex){

            }

            //System.out.println("scoreApi: " + scoreApi + " scannerAddress: "+scannerAddress);

            if(response != null) {
                return new HttpClientResponse(response.statusCode(),
                        response.headers().allValues("content-type"),
                        response.body());
            }
            return new HttpClientResponse(400, null, null);
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
