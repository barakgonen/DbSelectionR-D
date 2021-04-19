package org.analysis.db;

import org.common.structs.DbReadResponse;
import org.common.structs.DbTransactionMode;
import org.common.structs.StartSimulationResponse;
import org.common.structs.UpdateDataResponse;

public class ResultsAnalayzer {
    private DbTransactionMode insertionTransactionMode;
    private DbTransactionMode updatesTransactionMode;
    private int numberOfEntities;
    private int numberOfUpdates;
    private long insertionDuration;
    private long updateDuration; // For one by one method it would be the time for each 'save' operation, for BATCH
                                // it would be the time to precess the whole batch
    private DbReadResponse readResponse;

    public ResultsAnalayzer(StartSimulationResponse startSimulationResponse, UpdateDataResponse updateDataResponse, DbReadResponse dbReadResponse){
        insertionTransactionMode = startSimulationResponse.getDbTransactionMode();
        updatesTransactionMode = updateDataResponse.getDbTransactionMode();
        numberOfEntities = startSimulationResponse.getNumberOfEntities();
        numberOfUpdates = updateDataResponse.getNumberOfUpdates();
        insertionDuration = startSimulationResponse.getInsertionDuration();
        updateDuration = updateDataResponse.getTotalDuration();
        readResponse = dbReadResponse;
    }

    public String toCsv() {
        return numberOfEntities + "," + insertionTransactionMode + "," + insertionDuration + "," + numberOfUpdates + ","  + updatesTransactionMode + "," + updateDuration + "\n";
    }
}
