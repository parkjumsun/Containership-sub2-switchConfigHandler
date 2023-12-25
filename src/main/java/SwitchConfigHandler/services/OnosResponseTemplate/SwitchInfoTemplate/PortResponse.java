package SwitchConfigHandler.services.OnosResponseTemplate.SwitchInfoTemplate;

public class PortResponse {
    String port;
    Annotations annotations;

    public String getPort() {
        return port;
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public String getPortName(){
        return annotations.getPortName();
    }

    public String getMac(){
        return annotations.getPortMac();
    }

    public static class Annotations {
        String portName;
        String portMac;


        public String getPortName() {
            return portName;
        }

        public String getPortMac() {
            return portMac;
        }
    }

}
