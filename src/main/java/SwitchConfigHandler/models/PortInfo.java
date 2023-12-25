package SwitchConfigHandler.models;

public class PortInfo {
    private String name;
    private String swName;
    private String portNumber;

    private String portMac;


    public PortInfo(String name, String swName, String portNumber, String portMac) {
        this.name = name;
        this.swName = swName;
        this.portNumber = portNumber;
        this.portMac = portMac;
    }

    public String getName() {
        return name;
    }

    public String fetchSwitchName(){
        return this.swName;
    }

    public String fetchPortNum(){
        return this.portNumber;
    }

    public String fetchPortMac(){return this.portMac;}

}
