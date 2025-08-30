package bot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

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
    public String interrogateScanner(boolean localContentTest, boolean timerUpdate) {
        String scores = "", scoresTmp = "";
        int cnt = 0;
        for (Map.Entry<String, FortaData> entry : this.forta.entrySet()) {
            scoresTmp = "";
            for (FortaData.ScannerAddress node : entry.getValue().getForta_scanner_address()) {
                HttpClientResponse rsp = this.hcl.interrogate(entry.getValue().getScore_api_url()+this.fortaSeparator+node.getScanner_address());
                String fortaScore = rsp.getScore(localContentTest);
                String anh = "Healthy name >" + node.getScanner_address() +">"+node.getScanner_name() + " " + fortaScore + "\n";
                boolean allHealthy = false;
                try {
                    allHealthy = (Float.compare(Float.parseFloat(fortaScore), entry.getValue().getTrigger_value()) > 0);
                } catch (NumberFormatException e) {
                    System.out.println("BotDataDefinition.java - NumberFormatException " + fortaScore + " " +entry.getValue().getTrigger_value());
                }
                scoresTmp = this.getScoreString(scoresTmp, rsp, allHealthy, timerUpdate, ++cnt, anh);
            }
            if(!"".equals(scoresTmp)) {
                scores += "API>"+entry.getValue().getScore_api_url()+">\n" + scoresTmp;
            }

            cnt = 0;
        }
        return scores;
    }
    public String interrogateNode(boolean localContentTest, boolean timerUpdate) {
        String scores = "", scoresTmp = "";
        int cnt = 0;
        for (Map.Entry<String, StorjData> entry : this.storj.entrySet()) {
            scoresTmp = "";
            for (StorjData.NodeAddress node : entry.getValue().getStorj_node_address()) {
                HttpClientResponse rsp = this.hcl.interrogate(entry.getValue().getScore_api_url()+this.storjSeparator+node.getNode_address());
                boolean allHealthy = Boolean.parseBoolean(rsp.getScore(localContentTest));
                String anh = "Healthy name >" + node.getNode_address() +">"+node.getNode_name() + " " + allHealthy + "\n";
                scoresTmp = this.getScoreString(scoresTmp, rsp, allHealthy, timerUpdate, ++cnt, anh);
            }
            if(!"".equals(scoresTmp)) {
                scores += "API>"+entry.getValue().getScore_api_url()+">\n" + scoresTmp;
            }

            cnt = 0;
        }
        return scores;
    }

    private String getScoreString(String scoresTmp, HttpClientResponse rsp, boolean allHealthy, boolean timerUpdate, int cnt, String anh){
        if (rsp.getStatusCode() == 200) {
            if (allHealthy) {
                if (!timerUpdate) {
                    scoresTmp += cnt + " " + anh;
                }
            } else {
                scoresTmp += cnt + " NOT " + anh;
            }
        } else {
            scoresTmp += cnt + " NOT KNOWN " + anh;
        }
        return scoresTmp;
    }
}
