package bot.telegram;

import java.util.List;

public class FortaData {
    private Float trigger_value;
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

    public Float getTrigger_value() {
        return trigger_value;
    }

    public List<ScannerAddress> getForta_scanner_address() {
        return forta_scanner_address;
    }
}
