package bot.telegram;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.security.SecureRandom;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientLocal {
    public HttpClientResponse interrogate(String address) {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) { }
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) { }
            }
        };
        // Install the all-trusting trust manager
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        // Create an HttpClient that uses the custom
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .sslContext(sslContext)
                .build();;
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
