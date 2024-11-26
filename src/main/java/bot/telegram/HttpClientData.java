package bot.telegram;

public class HttpClientData {
    String address;
    String name;

    public HttpClientData(String name, String address){
        this.name = name;
        this.address = address;
    }

    public String getClientAddress(){
        return this.address;
    }

    public String getClientName(){
        return this.name;
    }
}
