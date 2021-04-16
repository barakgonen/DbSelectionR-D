package org.example;

import org.common.structs.DbTransactionMode;
import org.common.structs.StartSimulationResponse;
import org.common.structs.UpdateDataResponse;

public class ResultsAnalayzer {
    DbTransactionMode insertionTransactionMode;
    DbTransactionMode updatesTransactionMode;
    int numberOfEntities;
    int numberOfUpdates;
    long insertionDuration;
    long updateDuration; // For one by one method it would be the time for each 'save' operation, for BATCH
                                // it would be the time to precess the whole batch

    public ResultsAnalayzer(StartSimulationResponse startSimulationResponse, UpdateDataResponse updateDataResponse){
        insertionTransactionMode = startSimulationResponse.getDbTransactionMode();
        updatesTransactionMode = updateDataResponse.getDbTransactionMode();
        numberOfEntities = startSimulationResponse.getNumberOfEntities();
        numberOfUpdates = updateDataResponse.getNumberOfUpdates();
        insertionDuration = startSimulationResponse.getInsertionDuration();
        updateDuration = updateDataResponse.getTotalDuration();
    }

    public String toCsv() {
        return numberOfEntities + "," + insertionTransactionMode + "," + insertionDuration + "," + numberOfUpdates + ","  + updatesTransactionMode + "," + updateDuration + "\n";
    }
}
