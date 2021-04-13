package org.common.structs;

public class SimulationRequest {
    private int numberOfEntities;
    private int numberOfUpdates;

    public SimulationRequest(){

    }

    public SimulationRequest(int numberOfEntities, int numberOfUpdates) {
        this.numberOfEntities = numberOfEntities;
        this.numberOfUpdates = numberOfUpdates;
    }

    public int getNumberOfEntities() {
        return numberOfEntities;
    }

    public void setNumberOfEntities(int numberOfEntities) {
        this.numberOfEntities = numberOfEntities;
    }

    public int getNumberOfUpdates() {
        return numberOfUpdates;
    }

    public void setNumberOfUpdates(int numberOfUpdates) {
        this.numberOfUpdates = numberOfUpdates;
    }
}
