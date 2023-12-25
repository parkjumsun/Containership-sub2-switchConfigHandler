package SwitchConfigHandler.models;

import java.util.List;

public class SwitchInfo {

    private List<String> portList;
    private String internalMac;
    public List<String> getPortList() {
        return portList;
    }

    public void setPortList(List<String> portList) {
        this.portList = portList;
    }

    public void setInternalMac(String internalMac) {
        this.internalMac = internalMac;
    }

    public String fetchInternalMac() {
        return internalMac;
    }
}
