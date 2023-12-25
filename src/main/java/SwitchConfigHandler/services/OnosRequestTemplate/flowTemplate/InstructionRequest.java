package SwitchConfigHandler.services.OnosRequestTemplate.flowTemplate;

public class InstructionRequest {

        String type;
        String subtype;
        String mac;
        String port;


        public InstructionRequest setType(String type) {
            this.type = type;
            return this;
        }

        public InstructionRequest setSubtype(String subtype) {
            this.subtype = subtype;
            return this;
        }

        public InstructionRequest setMac(String mac) {
            this.mac = mac;
            return this;
        }

        public InstructionRequest setPort(String port) {
            this.port = port;
            return this;
        }

        public String getType() {
            return type;
        }

        public String getSubtype() {
            return subtype;
        }

        public String getMac() {
            return mac;
        }

        public String getPort() {
            return port;
        }
}
