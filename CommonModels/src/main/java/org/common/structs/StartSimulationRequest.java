package org.common.structs;

public class StartSimulationRequest {
    private int numberOfEntities;
    private DbTransactionMode transactionMode;

    public StartSimulationRequest(){
    }

    public StartSimulationRequest(int numberOfEntities, DbTransactionMode dbTransactionMode) {
        this.numberOfEntities = numberOfEntities;
        this.transactionMode = dbTransactionMode;
    }

    public int getNumberOfEntities() {
        return numberOfEntities;
    }

    public DbTransactionMode getTransactionMode(){
        return transactionMode;
    }

//    public void setNumberOfEntities(int numberOfEntities) {
//        this.numberOfEntities = numberOfEntities;
//    }
    @Override
    public String toString(){
        return "Number of entities: " + numberOfEntities;
    }
}
