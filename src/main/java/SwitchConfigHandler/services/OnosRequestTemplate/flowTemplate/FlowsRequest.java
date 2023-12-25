package SwitchConfigHandler.services.OnosRequestTemplate.flowTemplate;

import java.util.ArrayList;
import java.util.List;

public class FlowsRequest {
    List<FlowRequest> flows = new ArrayList<>();

    public FlowsRequest(List<FlowRequest> flows) {
        this.flows = flows;
    }

    public List<FlowRequest> getFlows() {
        return flows;
    }
}
