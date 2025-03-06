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
        String scores = "", scoresTmp = "";
        Integer cnt = 0;
        for (Map.Entry<String, FortaData> entry : this.forta.entrySet()) {
            scoresTmp = "";
            for (FortaData.ScannerAddress node : entry.getValue().getForta_scanner_address()) {
                HttpClientResponse rsp = this.hcl.interrogate(entry.getValue().getScore_api_url()+this.fortaSeparator+node.getScanner_address());
                String fortaScore = rsp.getScore(localContentTest);
                String anh = "Healthy name >" + node.getScanner_address() +">"+node.getScanner_name() + " " + fortaScore + "\n";
                cnt++;
                if (rsp.getStatusCode() == 200) {
                    try {
                        if ((Float.compare(Float.parseFloat(fortaScore), entry.getValue().getTrigger_value()) < 0)) {
                            scoresTmp += cnt + " NOT " + anh;
                        } else if (!timerUpdate) {
                            scoresTmp += cnt + " " + anh;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("BotDataDefinition.java - NumberFormatException");
                    }
                } else {
                    scoresTmp += cnt + " NOT KNOWN " + anh;
                }
            }
            if(!"".equals(scoresTmp)) {
                scores += "API>"+entry.getValue().getScore_api_url()+">\n" + scoresTmp;
            }

            cnt = 0;
        }
        return scores;
    }
    public String interrogateNode(Boolean localContentTest, Boolean timerUpdate) {
        String scores = "", scoresTmp = "";
        Integer cnt = 0;
        for (Map.Entry<String, StorjData> entry : this.storj.entrySet()) {
            scoresTmp = "";
            for (StorjData.NodeAddress node : entry.getValue().getStorj_node_address()) {
                HttpClientResponse rsp = this.hcl.interrogate(entry.getValue().getScore_api_url()+this.storjSeparator+node.getNode_address());
                boolean allHealthy = Boolean.parseBoolean(rsp.getScore(localContentTest));
                String anh = "Healthy name >" + node.getNode_address() +">"+node.getNode_name() + " " + allHealthy + "\n";
                cnt++;
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
            }
            if(!"".equals(scoresTmp)) {
                scores += "API>"+entry.getValue().getScore_api_url()+">\n" + scoresTmp;
            }

            cnt = 0;
        }
        return scores;
    }
}
