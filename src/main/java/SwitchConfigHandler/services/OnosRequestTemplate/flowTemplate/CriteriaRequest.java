package SwitchConfigHandler.services.OnosRequestTemplate.flowTemplate;

public class CriteriaRequest {
        String type;
        String ethType;
        String ip;



        public String getType() {
            return type;
        }

        public String getEthType() {
            return ethType;
        }

        public String getIp() {
            return ip;
        }

        public CriteriaRequest setType(String type) {
            this.type = type;
            return this;
        }

        public CriteriaRequest setEthType(String ethType) {
            this.ethType = ethType;
            return this;
        }

        public CriteriaRequest setIp(String ip) {
            this.ip = ip;
            return this;
        }
}
