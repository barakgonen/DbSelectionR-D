package org.common.structs;

public class StartSimulationRequest {
    private int numberOfEntities;

    public StartSimulationRequest(){
    }

    public StartSimulationRequest(int numberOfEntities) {
        this.numberOfEntities = numberOfEntities;
    }

    public int getNumberOfEntities() {
        return numberOfEntities;
    }

    public void setNumberOfEntities(int numberOfEntities) {
        this.numberOfEntities = numberOfEntities;
    }
}
