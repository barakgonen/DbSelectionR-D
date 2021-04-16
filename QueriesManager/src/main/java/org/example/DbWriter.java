package org.example;

import org.common.structs.StartSimulationResponse;
import org.common.structs.UpdateDataResponse;
import org.infra.IGenericDbWriter;
import org.springframework.data.util.Pair;

import java.util.concurrent.Callable;

public class DbWriter implements Callable<Pair<StartSimulationResponse, UpdateDataResponse>> {

    private IGenericDbWriter dbWriter;
    public DbWriter(IGenericDbWriter specificWriter){
        dbWriter = specificWriter;
    }

    @Override
    public Pair<StartSimulationResponse, UpdateDataResponse> call() {
        StartSimulationResponse startSimulationResponse = dbWriter.insertToDb();
        UpdateDataResponse updateDataResponse = dbWriter.periodicUpdates();
        return Pair.of(startSimulationResponse, updateDataResponse);
    }
}
