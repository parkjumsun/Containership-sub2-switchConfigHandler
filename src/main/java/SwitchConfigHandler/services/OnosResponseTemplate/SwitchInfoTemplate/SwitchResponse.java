package SwitchConfigHandler.services.OnosResponseTemplate.SwitchInfoTemplate;

public class SwitchResponse {
    private String id;
    private String type;
    Boolean available;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Boolean getAvailable() {
        return available;
    }
}
