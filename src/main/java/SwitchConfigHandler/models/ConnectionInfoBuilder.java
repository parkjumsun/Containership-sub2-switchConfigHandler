package SwitchConfigHandler.models;

public class ConnectionInfoBuilder {
    private String swName;
    private String switchMac;
    private String portNum;
    private String containerIP;
    private String containerMac;

    public ConnectionInfoBuilder setSwName(String swName) {
        this.swName = swName;
        return this;
    }

    public ConnectionInfoBuilder setSwitchMac(String switchMac) {
        this.switchMac = switchMac;
        return this;
    }

    public ConnectionInfoBuilder setPortNum(String portNum) {
        this.portNum = portNum;
        return this;
    }

    public ConnectionInfoBuilder setContainerIP(String containerIP) {
        this.containerIP = containerIP;
        return this;
    }

    public ConnectionInfoBuilder setContainerMac(String containerMac) {
        this.containerMac = containerMac;
        return this;
    }

    public ConnectionInfo getConnectionInfo(){
        return new ConnectionInfo(swName, switchMac, portNum, containerIP, containerMac);
    }
}
