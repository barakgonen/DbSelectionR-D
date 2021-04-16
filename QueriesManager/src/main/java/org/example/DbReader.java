package org.example;

import org.common.structs.DbReadResponse;
import org.common.structs.StartSimulationResponse;
import org.common.structs.UpdateDataResponse;
import org.infra.IGenericDbWriter;
import org.springframework.data.util.Pair;

import java.util.concurrent.Callable;

public class DbReader implements Callable<DbReadResponse> {

    private IGenericDbWriter dbWriter;
    public DbReader(IGenericDbWriter specificWriter){
        dbWriter = specificWriter;
    }

    @Override
    public DbReadResponse call() {
        return new DbReadResponse(null);
    }
}
