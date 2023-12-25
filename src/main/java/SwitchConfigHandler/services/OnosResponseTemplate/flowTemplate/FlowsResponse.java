package SwitchConfigHandler.services.OnosResponseTemplate.flowTemplate;

import java.util.ArrayList;
import java.util.List;

public class FlowsResponse {
    List<FlowResponse> flows = new ArrayList<>();

    public List<FlowResponse> getFlows() {
        return flows;
    }
}
