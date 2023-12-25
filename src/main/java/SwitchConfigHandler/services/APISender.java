package SwitchConfigHandler.services;

import SwitchConfigHandler.models.ConnectionInfo;
import SwitchConfigHandler.models.PortInfo;
import SwitchConfigHandler.models.SwitchInfo;
import SwitchConfigHandler.models.ResponseTemplate.MessageTemplate.MessageResponse;

import java.util.List;
import java.util.Map;

public interface APISender {
    Map<String, SwitchInfo> fetchSwitchInfo();
    List<PortInfo> fetchPortInfo(String switchName);

    MessageResponse configureARP(List<String> switchName);
            //switchName, portMac, portNum, containerIP, containerMac,
    MessageResponse connectContainers(ConnectionInfo c1, ConnectionInfo c2);

}
