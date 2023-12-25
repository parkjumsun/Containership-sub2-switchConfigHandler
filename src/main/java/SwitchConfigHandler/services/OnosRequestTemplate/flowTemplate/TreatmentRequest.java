package SwitchConfigHandler.services.OnosRequestTemplate.flowTemplate;

import java.util.ArrayList;
import java.util.List;

public class TreatmentRequest {
    List<InstructionRequest> instructions = new ArrayList<>();

    public TreatmentRequest(List<InstructionRequest> instructions) {
        this.instructions = instructions;
    }

    public TreatmentRequest(InstructionRequest instructionRequest){
        this.instructions.add(instructionRequest);
    }


    public List<InstructionRequest> getInstructions() {
        return instructions;
    }

}
