package bot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;

public class BotDataDefinition {
    @JsonProperty("botInfo")
    BotInfo botInfo;
    @JsonProperty("forta")
    private Map<String, FortaData> forta;
    @JsonProperty("storj")
    private Map<String, StorjData> storj;
    private HttpClientLocal hcl = new HttpClientLocal();
    String fortaSeparator = "";
    String storjSeparator = ":";

    public BotDataDefinition() { }

    // Getters și Setters
    public BotInfo botInfo() {
        System.out.println("Bot>>>> {"+this.botInfo.getTelegramBotName() + " " + this.botInfo.getTelegramBotToken()+"}");
        return botInfo;
    }
    public String interrogateScanner(Boolean localContentTest, Boolean timerUpdate) {
        String scores = "", scoresAPI_URL = "", scoresTmp = "";
        Integer cnt = 0;
        for (Map.Entry<String, FortaData> entry : this.forta.entrySet()) {
            scoresTmp = "";
            scoresAPI_URL = "API>"+entry.getValue().getScore_api_url()+">\n";
            for (FortaData.ScannerAddress node : entry.getValue().getForta_scanner_address()) {
                HttpClientResponse rsp = this.hcl.interrogate(entry.getValue().getScore_api_url()+this.fortaSeparator+node.getScanner_address());
                String fortaScore = rsp.getScore(localContentTest);
                cnt++;
                try{
                    if((Float.compare(Float.parseFloat(fortaScore), 0.8f) < 0)) {
                        scoresTmp += cnt+" NOT Healthy name >" + node.getScanner_address() +">"+node.getScanner_name() + " " + fortaScore + "\n";
                    } else if (!timerUpdate){
                        scoresTmp += cnt+" Healthy name >" + node.getScanner_address() +">"+node.getScanner_name() + " " + fortaScore + "\n";
                    }
                } catch (NumberFormatException e) {
                    System.out.println("BotDataDefinition.java - NumberFormatException");
                }
            }
            if(!Objects.equals(scoresTmp, "")) {
                scores += scoresAPI_URL + scoresTmp;
            }

            cnt = 0;
        }
        return scores;
    }
    public String interrogateNode(Boolean localContentTest, Boolean timerUpdate) {
        String scores = "", scoresAPI_URL = "", scoresTmp = "";
        Integer cnt = 0;
        for (Map.Entry<String, StorjData> entry : this.storj.entrySet()) {
            scoresTmp = "";
            scoresAPI_URL = "API>"+entry.getValue().getScore_api_url()+">\n";
            for (StorjData.NodeAddress node : entry.getValue().getStorj_node_address()) {
                HttpClientResponse rsp = this.hcl.interrogate(entry.getValue().getScore_api_url()+this.storjSeparator+node.getNode_address());
                boolean allHealthy = Boolean.parseBoolean(rsp.getScore(localContentTest));
                cnt++;
                if(allHealthy) {
                    if (!timerUpdate) {
                        scoresTmp += cnt+" Healthy name >" + node.getNode_address() +">"+node.getNode_name() + " " + allHealthy + "\n";
                    }
                } else {
                    scoresTmp += cnt+" NOT Healthy name >" + node.getNode_address()+">"+node.getNode_name() + " " + allHealthy + "\n";
                }
            }
            if(!Objects.equals(scoresTmp, "")) {
                scores += scoresAPI_URL + scoresTmp;
            }

            cnt = 0;
        }
        return scores;
    }
}
