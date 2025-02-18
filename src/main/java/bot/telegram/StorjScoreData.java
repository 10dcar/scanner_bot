package bot.telegram;

public class StorjScoreData {
    private String statuses;
    private String help;
    private boolean allHealthy;

    public StorjScoreData() {
        this.statuses = null;
        this.help = "To access Storagenode services, please use DRPC protocol!";
        this.allHealthy = true;
    }

    // Getter and Setter methods
    public String getStatuses() {
        return statuses;
    }

    public void setStatuses(String statuses) {
        this.statuses = statuses;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public boolean isAllHealthy() {
        return allHealthy;
    }

    public void setAllHealthy(boolean allHealthy) {
        this.allHealthy = allHealthy;
    }

    @Override
    public String toString() {
        return "FortaLinuxJavaScannerBot{" +
                "statuses='" + statuses + '\'' +
                ", help='" + help + '\'' +
                ", allHealthy=" + allHealthy +
                '}';
    }
}
