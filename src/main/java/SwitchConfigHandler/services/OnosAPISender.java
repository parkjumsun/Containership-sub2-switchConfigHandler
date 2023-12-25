package SwitchConfigHandler.services;


import SwitchConfigHandler.models.ConnectionInfo;
import SwitchConfigHandler.models.PortInfo;
import SwitchConfigHandler.models.SwitchInfo;
import SwitchConfigHandler.services.OnosRequestTemplate.flowTemplate.*;
import SwitchConfigHandler.models.ResponseTemplate.MessageTemplate.MessageResponse;
import SwitchConfigHandler.services.OnosResponseTemplate.SwitchInfoTemplate.PortListResponse;
import SwitchConfigHandler.services.OnosResponseTemplate.SwitchInfoTemplate.PortResponse;
import SwitchConfigHandler.services.OnosResponseTemplate.SwitchInfoTemplate.SwitchListResponse;
import SwitchConfigHandler.services.OnosResponseTemplate.SwitchInfoTemplate.SwitchResponse;
import SwitchConfigHandler.services.OnosResponseTemplate.flowTemplate.FlowsResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static SwitchConfigHandler.services.OnosRequestTemplate.flowTemplate.SelectorRequest.*;
import static SwitchConfigHandler.services.OnosRequestTemplate.flowTemplate.TreatmentRequest.*;

@Component
public class OnosAPISender implements APISender {
    private String ip = "192.168.219.100";
    private String port = "8181";

    private String username = "karaf";
    private String password = "karaf";




    private final RestTemplate restTemplate;

    public OnosAPISender() {
        restTemplate = new RestTemplate();
    }

    @Override
    public Map<String, SwitchInfo> fetchSwitchInfo() {
        String url = "http://" + ip + ":" + port + "/onos/v1/devices/";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<SwitchListResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, SwitchListResponse.class);
        SwitchListResponse switchListResponse = responseEntity.getBody();

        List<SwitchResponse> devices = switchListResponse.getDevices();
        Map<String,SwitchInfo> switchInfoList = new HashMap<>();

        for(SwitchResponse device: devices){
            if(device.getType().equals("SWITCH") && device.getAvailable()){
                switchInfoList.put(device.getId(), new SwitchInfo());
            }
        }
        return switchInfoList;
    }

    @Override
    public List<PortInfo> fetchPortInfo(String switchName) {
        String url = "http://" + ip + ":" + port + "/onos/v1/devices/" + switchName + "/ports" ;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<PortListResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, PortListResponse.class);
        PortListResponse response = responseEntity.getBody();
        List<PortResponse> ports = response.getPorts();
        List<PortInfo> portInfoList = new ArrayList<>();
        for (PortResponse port: ports){
            portInfoList.add(new PortInfo(port.getPortName(), switchName, port.getPort(), port.getMac()));
        }

        return portInfoList;
    }

    @Override
    public MessageResponse configureARP(List<String> switchName) {

        List<FlowRequest> flows = new ArrayList<>();
        for (String name: switchName){
            InstructionRequest instructionRequest = new InstructionRequest();
            instructionRequest.setType("OUTPUT")
                    .setPort("NORMAL");

            CriteriaRequest criteriaRequest = new CriteriaRequest();
            criteriaRequest.setType("ETH_TYPE")
                    .setEthType("0x806");

            TreatmentRequest treatmentRequest = new TreatmentRequest(instructionRequest);
            SelectorRequest selectorRequest = new SelectorRequest(criteriaRequest);

            FlowRequest flowRequest = new FlowRequest();
            flowRequest
                    .setPriority("60000")
                    .setTimeout("0")
                    .setPermanent(true)
                    .setDeviceId(name)
                    .setSelector(selectorRequest)
                    .setTreatment(treatmentRequest);
            flows.add(flowRequest);
        }

        FlowsRequest flowsRequest = new FlowsRequest(flows);


        String url = "http://" + ip + ":" + port + "/onos/v1/flows";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FlowsRequest> entity = new HttpEntity<>(flowsRequest, headers);
        ResponseEntity<FlowsResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, FlowsResponse.class);
        return new MessageResponse("configured successfully");
    }

    @Override
    public MessageResponse connectContainers(ConnectionInfo c1, ConnectionInfo c2) {
        List<FlowRequest> flows = new ArrayList<>();
        if(!c1.getSwName().equals(c2.getSwName())) {
            flows.add(makeConnectionRule(c1.getSwitchMac(), c2.getSwitchMac(), "1", c1.getContainerIP(), c2.getContainerIP(), c1.getSwName()));
            flows.add(makeConnectionRule(c2.getSwitchMac(), c2.getContainerMac(), c2.getPortNum(), c1.getContainerIP(), c2.getContainerIP(), c2.getSwName()));
            flows.add(makeConnectionRule(c2.getSwitchMac(), c1.getSwitchMac(), "1", c2.getContainerIP(), c1.getContainerIP(), c2.getSwName()));
            flows.add(makeConnectionRule(c1.getSwitchMac(), c1.getContainerMac(), c1.getPortNum(), c2.getContainerIP(), c1.getContainerIP(), c1.getSwName()));
        }
        else{
            flows.add(makeConnectionRule(c1.getSwitchMac(), c2.getContainerMac(), c2.getPortNum(), c1.getContainerIP(), c2.getContainerIP(), c1.getSwName()));
            flows.add(makeConnectionRule(c1.getSwitchMac(), c1.getContainerMac(), c1.getPortNum(), c2.getContainerIP(), c1.getContainerIP(), c1.getSwName()));
        }

        FlowsRequest flowsRequest = new FlowsRequest(flows);
        String url = "http://" + ip + ":" + port + "/onos/v1/flows";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FlowsRequest> entity = new HttpEntity<>(flowsRequest, headers);
        ResponseEntity<FlowsResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, FlowsResponse.class);
        return new MessageResponse("configured successfully");
    }

    FlowRequest makeConnectionRule(String srcMac, String dstMac, String portNum, String srcIp, String dstIp, String switchName){
        List<InstructionRequest> instructionRequestList  = new ArrayList<>();

        InstructionRequest instructionRequest1 = new InstructionRequest();
        instructionRequest1.setType("L2MODIFICATION")
                .setSubtype("ETH_SRC")
                .setMac(srcMac);

        InstructionRequest instructionRequest2 = new InstructionRequest();
        instructionRequest2.setType("L2MODIFICATION")
                .setSubtype("ETH_DST")
                .setMac(dstMac);

        InstructionRequest instructionRequest3 = new InstructionRequest();
        instructionRequest3.setType("OUTPUT");
        instructionRequest3.setPort(portNum);

        instructionRequestList.add(instructionRequest1);
        instructionRequestList.add(instructionRequest2);
        instructionRequestList.add(instructionRequest3);


        List<CriteriaRequest> criteriaRequestList = new ArrayList<>();

        CriteriaRequest criteriaRequest1 = new CriteriaRequest();
        criteriaRequest1.setType("ETH_TYPE")
                .setEthType("0x800");

        CriteriaRequest criteriaRequest2 = new CriteriaRequest();
        criteriaRequest2.setType("IPV4_SRC")
                .setIp(srcIp + "/32");

        CriteriaRequest criteriaRequest3 = new CriteriaRequest();
        criteriaRequest3.setType("IPV4_DST")
                .setIp(dstIp + "/32");

        criteriaRequestList.add(criteriaRequest1);
        criteriaRequestList.add(criteriaRequest2);
        criteriaRequestList.add(criteriaRequest3);

        TreatmentRequest treatmentRequest = new TreatmentRequest(instructionRequestList);
        SelectorRequest selectorRequest = new SelectorRequest(criteriaRequestList);

        FlowRequest flow = new FlowRequest();
        flow.setPriority("50000")
                .setTimeout("0")
                .setPermanent(true)
                .setDeviceId(switchName)
                .setSelector(selectorRequest)
                .setTreatment(treatmentRequest);
        return flow;
    }


}
