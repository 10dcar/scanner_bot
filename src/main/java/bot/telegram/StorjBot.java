package bot.telegram;

public class StorjBot implements Bot{
    String name;
    String address;
    String url;
    String token;

    public StorjBot(String name, String address, String url, String token) {
        this.name = name;
        this.address = address;
        this.url = url;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return "Scorul actual Storj este: ";
    }
}
