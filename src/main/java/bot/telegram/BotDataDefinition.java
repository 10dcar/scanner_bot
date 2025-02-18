package bot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    public String interrogateScanner(Boolean localContentTest, Boolean timerUpdate) {
        String scores = "";
        for (Map.Entry<String, FortaData> entry : this.forta.entrySet()) {
            System.out.println("Field " + entry.getKey() + ":");
            System.out.println("  Score API URL: " + entry.getValue().getScore_api_url());
            for (FortaData.ScannerAddress node : entry.getValue().getForta_scanner_address()) {
                System.out.println("  Node Address: " + node.getScanner_address() + ", Node Name: " + node.getScanner_name());
                HttpClientResponse rsp = this.hcl.interrogate(entry.getValue().getScore_api_url()+this.fortaSeparator+node.getScanner_address());
                //daca < 0.8 atunci pun intr un array list sa numar cate au erori si trec eroare in dreptul lui
                String fortaScore = rsp.getScore(localContentTest);
                try{
                    if((Float.compare(Float.parseFloat(fortaScore), 0.8f) < 0)) {
                        scores += "NOT Healthy name: " + node.getScanner_name() + " " + rsp.getScore(localContentTest) + "\n";
                    } else if (!timerUpdate){
                        scores += "Healthy name: " + node.getScanner_name() + " " + rsp.getScore(localContentTest) + "\n";
                    }
                } catch (NumberFormatException e) {
                    System.out.println("BotDataDefinition.java - NumberFormatException");
                }
            }
        }
        return scores;
    }
    public String interrogateNode(Boolean localContentTest, Boolean timerUpdate) {
        String scores = "";
        for (Map.Entry<String, StorjData> entry : this.storj.entrySet()) {
            System.out.println("Field " + entry.getKey() + ":");
            System.out.println("Score API URL: " + entry.getValue().getScore_api_url());
            for (StorjData.NodeAddress node : entry.getValue().getStorj_node_address()) {
                System.out.println("Node Address: " + node.getNode_address() + ", Node Name: " + node.getNode_name());
                HttpClientResponse rsp = this.hcl.interrogate(entry.getValue().getScore_api_url()+this.storjSeparator+node.getNode_address());
                //undeva aici ar trebui sa decida ce e cu el: daca raspunde 404 ce inseamna etc

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode;
                boolean allHealthy = false;
                try {
                    rootNode = objectMapper.readTree(rsp.getScore(localContentTest));
                    allHealthy = rootNode.get("AllHealthy").asBoolean();
                } catch (JsonProcessingException jpe){
                    System.out.println("BotDataDefinition.java - JsonProcessingException");
                } catch (IllegalArgumentException iae){
                    System.out.println("BotDataDefinition.java - IllegalArgumentException");
                }
                //daca rsp.getScore() transform in obiect si daca "AllHealthy" != true
                if(allHealthy) {
                    if (!timerUpdate) {
                        scores += "Healthy name: " + node.getNode_name() + " " + allHealthy + "\n";
                    }
                } else {
                    scores += "NOT Healthy name: " + node.getNode_name() + " " + allHealthy + "\n";
                }
            }
        }
        return scores;
    }
}
