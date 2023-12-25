package SwitchConfigHandler.services.OnosRequestTemplate.flowTemplate;


public class FlowRequest {
    String priority;
    String timeout;
    Boolean isPermanent;
    String deviceId;
    TreatmentRequest treatment;
    SelectorRequest selector;

    public FlowRequest setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public FlowRequest setTimeout(String timeout) {
        this.timeout = timeout;
        return this;
    }

    public FlowRequest setPermanent(Boolean permanent) {
        this.isPermanent = permanent;
        return this;
    }

    public FlowRequest setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public FlowRequest setTreatment(TreatmentRequest treatment) {
        this.treatment = treatment;
        return this;
    }

    public FlowRequest setSelector(SelectorRequest selector) {
        this.selector = selector;
        return this;
    }


    public String getPriority() {
        return priority;
    }

    public String getTimeout() {
        return timeout;
    }

    public Boolean getIsPermanent() {
        return isPermanent;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public TreatmentRequest getTreatment() {
        return treatment;
    }

    public SelectorRequest getSelector() {
        return selector;
    }
}