package SwitchConfigHandler.controller;
import SwitchConfigHandler.models.ConnectionInfo;
import SwitchConfigHandler.models.ConnectionInfoBuilder;
import SwitchConfigHandler.models.RequsetTemplate.ContainerConnectionInfo;
import SwitchConfigHandler.services.APISender;
import SwitchConfigHandler.models.PortInfo;
import SwitchConfigHandler.models.SwitchInfo;
import SwitchConfigHandler.models.ResponseTemplate.MessageTemplate.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class SwitchConfigHandler {
    private Map<String, SwitchInfo> swMap;
    private Map<String, PortInfo> grMap;
    private APISender sender;

    @Autowired
    public SwitchConfigHandler(APISender sender) {
        swMap = new HashMap<>();
        grMap = new HashMap<>();
        this.sender = sender;
        this.getSwitches();
        this.initSwitch();
    }

    @GetMapping("/switches")
    public Map<String, SwitchInfo> getSwitches(){
        Map<String, SwitchInfo> switchInfoList = sender.fetchSwitchInfo();
        for(String switchName: switchInfoList.keySet()) {
            List<PortInfo> portInfoList = sender.fetchPortInfo(switchName);
            SwitchInfo switchInfo = switchInfoList.get(switchName);
            List<String> portStringList = new ArrayList<>();
            for(PortInfo port: portInfoList){
                grMap.put(port.getName(), port);
                portStringList.add(port.getName());
                if(port.getName().contains("gw"))
                    switchInfo.setInternalMac(port.fetchPortMac());
            }
            switchInfo.setPortList(portStringList);
        }
        this.swMap = switchInfoList;
        return this.swMap;
    }

    @GetMapping("/switches/init")
    public MessageResponse initSwitch(){
        List<String> switchNames = new ArrayList<>();
        for(String switchName: swMap.keySet()) {
            switchNames.add(switchName);
        }
        MessageResponse messageResponse = this.sender.configureARP(switchNames);
        return messageResponse;
    }

    @PostMapping("/containers/connect")
    public MessageResponse connectContainers(@RequestBody ContainerConnectionInfo connectInfoFromUser){
        String c1GroupName = connectInfoFromUser.getC1GroupName();
        String c2GroupName = connectInfoFromUser.getC2GroupName();
        PortInfo c1PortInfo = this.grMap.get("br-" + c1GroupName);
        PortInfo c2PortInfo = this.grMap.get("br-" + c2GroupName);


        ConnectionInfoBuilder connectionInfoBuilder1 = new ConnectionInfoBuilder();
        ConnectionInfo connectionInfo1 = connectionInfoBuilder1
                .setContainerIP(connectInfoFromUser.getC1IP())
                .setContainerMac(connectInfoFromUser.getC1MAC())
                .setSwitchMac(swMap.get(c1PortInfo.fetchSwitchName()).fetchInternalMac())
                .setSwName(c1PortInfo.fetchSwitchName())
                .setPortNum(c1PortInfo.fetchPortNum())
                .getConnectionInfo();

        ConnectionInfoBuilder connectionInfoBuilder2 = new ConnectionInfoBuilder();
        ConnectionInfo connectionInfo2 = connectionInfoBuilder2
                .setContainerIP(connectInfoFromUser.getC2IP())
                .setContainerMac(connectInfoFromUser.getC2MAC())
                .setSwitchMac(swMap.get(c2PortInfo.fetchSwitchName()).fetchInternalMac())
                .setSwName((c2PortInfo.fetchSwitchName()))
                .setPortNum(c2PortInfo.fetchPortNum())
                .getConnectionInfo();

        sender.connectContainers(connectionInfo1, connectionInfo2);

        return new MessageResponse("connected successfully");
    }


}

