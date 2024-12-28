package bot.telegram;

public interface Bot {
    public String getName();
    public void setName(String name);
    public String getAddress();
    public void setAddress(String address);
    public String getUrl();
    public void setUrl(String url);
    public String getToken();
    public void setToken(String token);
    public String getMessage();
}