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
    public boolean isAllHealthy() {
        return allHealthy;
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
