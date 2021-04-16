package org.common.structs;

public class UpdateDataRequest {
    private int numberOfUpdates;
    private DbTransactionMode writingMethod;

    public UpdateDataRequest() {
    }

    public UpdateDataRequest(int numberOfUpdates, DbTransactionMode writingMethod) {
        this.numberOfUpdates = numberOfUpdates;
        this.writingMethod = writingMethod;
    }

    public int getNumberOfUpdates() {
        return numberOfUpdates;
    }

    public DbTransactionMode getWritingMethod(){
        return writingMethod;
    }
    @Override
    public String toString() {
        return "Number of updates: " + numberOfUpdates;
    }
}
