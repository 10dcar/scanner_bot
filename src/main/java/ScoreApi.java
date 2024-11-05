public class ScoreApi {
    String score_api_url;

    public ScoreApi(String score_api_url){
        this.score_api_url = score_api_url;
    }

    public String getApiUrl(){
        return this.score_api_url;
    }
}
