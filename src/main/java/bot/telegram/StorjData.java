package bot.telegram;

import java.util.List;

public class StorjData {
    private String score_api_url;
    private List<NodeAddress> storj_node_address;

    public static class NodeAddress {
        private String node_address;
        private String node_name;

        public String getNode_address() {
            return node_address;
        }

        public String getNode_name() {
            return node_name;
        }
    }

    public String getScore_api_url() {
        return score_api_url;
    }

    public List<NodeAddress> getStorj_node_address() {
        return storj_node_address;
    }
}
