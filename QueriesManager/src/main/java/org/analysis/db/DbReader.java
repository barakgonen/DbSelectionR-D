package org.analysis.db;

import org.analysis.db.api.DbReaderApiFactory;
import org.common.structs.DbReadRequest;
import org.common.structs.DbReadResponse;
import org.infra.IGenericDbReader;

import java.util.concurrent.Callable;

public class DbReader implements Callable<DbReadResponse> {

    private IGenericDbReader dbReader;
    private DbReadRequest readRequest;

    public DbReader(DbReadRequest readRequest) {
        dbReader = DbReaderApiFactory.getDbReaderApi();
        this.readRequest = readRequest;
    }

    @Override
    public DbReadResponse call() {
        return dbReader.queryDb(readRequest);
    }
}
