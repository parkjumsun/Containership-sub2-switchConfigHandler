package SwitchConfigHandler.models;

public class ConnectionInfo {
    private String swName;
    private String switchMac;
    private String portNum;
    private String containerIP;
    private String containerMac;

    public ConnectionInfo(String swName, String switchMac, String portNum, String containerIP, String containerMac){
        this.swName = swName;
        this.switchMac = switchMac;
        this.portNum = portNum;
        this.containerIP = containerIP;
        this.containerMac = containerMac;
    }

    public String getSwName() {
        return swName;
    }

    public String getSwitchMac() {
        return switchMac;
    }

    public String getPortNum() {
        return portNum;
    }

    public String getContainerIP() {
        return containerIP;
    }

    public String getContainerMac() {
        return containerMac;
    }
}
