package bot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class FortaData {
    private String score_api_url;
    private List<FortaData.ScannerAddress> forta_scanner_address;

    public static class ScannerAddress {
        private String scanner_address;
        private String scanner_name;

        public String getScanner_address() {
            return scanner_address;
        }

        public String getScanner_name() {
            return scanner_name;
        }
    }

    public String getScore_api_url() {
        return score_api_url;
    }

    public List<ScannerAddress> getForta_scanner_address() {
        return forta_scanner_address;
    }
}
