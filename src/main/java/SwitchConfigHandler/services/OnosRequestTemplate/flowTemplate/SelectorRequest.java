package SwitchConfigHandler.services.OnosRequestTemplate.flowTemplate;

import java.util.ArrayList;
import java.util.List;

public class SelectorRequest {
    List<CriteriaRequest> criteria = new ArrayList<>();

    public SelectorRequest(List<CriteriaRequest> criteria) {
        this.criteria = criteria;
    }
    public SelectorRequest(CriteriaRequest criteria) {
        this.criteria.add(criteria);
    }


    public List<CriteriaRequest> getCriteria() {
        return criteria;
    }


}
