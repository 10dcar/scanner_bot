package bot.telegram;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

public class HttpClientLocal {
    public HttpClientResponse interrogate(String address) {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        try {
            System.out.println("Call address::::"+address);
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(address))
                    .build();
            HttpResponse<String> response = null;
            try {
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (HttpConnectTimeoutException ex){
                //ex.printStackTrace();
            } catch (HttpTimeoutException ex){
                //ex.printStackTrace();
            } catch (java.net.ConnectException ex){
                //ex.printStackTrace();
            }

            System.out.print("scannerAddress::::: "+address);

            if(response != null) {
                System.out.println(" code::::: "+response.statusCode());
                return new HttpClientResponse(response.statusCode(),
                        response.headers().allValues("content-type"),
                        response.body());
            }
            System.out.println(" code::::: 400");
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
