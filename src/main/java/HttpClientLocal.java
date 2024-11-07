import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientLocal {
    HttpClientResponse hcr;

    public String interrogate(String scoreApi, String scannerAddress) {
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

            this.hcr = new HttpClientResponse(response.statusCode(),
                    response.headers().allValues("content-type"),
                    response.body());

            //driver.findElement(By.className("aaa-name")).getText();
            /*List<WebElement> elements = driver.findElements(By.className("aaa-name"));
            for(WebElement element: elements){
                //perform your logic here...
                System.out.println(element.getText());
            }*/

            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "";
        }
    }
}
