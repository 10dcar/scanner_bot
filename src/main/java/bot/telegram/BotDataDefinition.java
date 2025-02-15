package bot.telegram;

import com.fasterxml.jackson.annotation.JsonCreator;
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
    public String interrogateScanner() {
        String scores = null;
        for (Map.Entry<String, FortaData> entry : this.forta.entrySet()) {
            System.out.println("Field " + entry.getKey() + ":");
            System.out.println("  Score API URL: " + entry.getValue().getScore_api_url());
            for (FortaData.ScannerAddress node : entry.getValue().getForta_scanner_address()) {
                System.out.println("  Node Address: " + node.getScanner_address() + ", Node Name: " + node.getScanner_name());
                HttpClientResponse rsp = this.hcl.interrogate(entry.getValue().getScore_api_url()+this.fortaSeparator+node.getScanner_address());
                scores += "Name: " + this.botInfo.getTelegramBotName() + " " + rsp.getScore(false) + "\n";
            }
        }
        return scores;
    }
    public String interrogateNode() {
        String scores = null;
        for (Map.Entry<String, StorjData> entry : this.storj.entrySet()) {
            System.out.println("Field " + entry.getKey() + ":");
            System.out.println("  Score API URL: " + entry.getValue().getScore_api_url());
            for (StorjData.NodeAddress node : entry.getValue().getStorj_node_address()) {
                System.out.println("  Node Address: " + node.getNode_address() + ", Node Name: " + node.getNode_name());
                HttpClientResponse rsp = this.hcl.interrogate(entry.getValue().getScore_api_url()+this.storjSeparator+node.getNode_address());
                scores += "Name: " + this.botInfo.getTelegramBotName() + " " + rsp.getScore(false) + "\n";
            }
        }
        return scores;
    }
}
