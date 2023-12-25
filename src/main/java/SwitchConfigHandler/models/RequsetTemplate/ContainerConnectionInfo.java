package SwitchConfigHandler.models.RequsetTemplate;

public class ContainerConnectionInfo {
    private String c1IP;
    private String c1MAC;
    private String c1GroupName;

    private String c2IP;
    private String c2MAC;
    private String c2GroupName;

    public String getC1IP() {
        return c1IP;
    }

    public String getC1MAC() {
        return c1MAC;
    }

    public String getC1GroupName() {
        return c1GroupName;
    }

    public String getC2IP() {
        return c2IP;
    }

    public String getC2MAC() {
        return c2MAC;
    }

    public String getC2GroupName() {
        return c2GroupName;
    }

    public void setC1IP(String c1IP) {
        this.c1IP = c1IP;
    }

    public void setC1MAC(String c1MAC) {
        this.c1MAC = c1MAC;
    }

    public void setC1GroupName(String c1GroupName) {
        this.c1GroupName = c1GroupName;
    }

    public void setC2IP(String c2IP) {
        this.c2IP = c2IP;
    }

    public void setC2MAC(String c2MAC) {
        this.c2MAC = c2MAC;
    }

    public void setC2GroupName(String c2GroupName) {
        this.c2GroupName = c2GroupName;
    }
}
