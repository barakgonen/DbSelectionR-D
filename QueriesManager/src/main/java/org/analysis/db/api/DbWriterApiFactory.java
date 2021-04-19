package org.analysis.db.api;

import org.analysis.db.api.elasticsearch.ElasticSearchApiWriter;
import org.common.structs.DbTransactionMode;
import org.infra.IGenericDbWriter;

public class DbWriterApiFactory {
    private DbWriterApiFactory(){

    }

    public static IGenericDbWriter getDbReaderApi(int numOfEntities, int numOfUpdates){
        DbTransactionMode initialWriteMethod =
                DbTransactionMode.valueOf(System.getenv().getOrDefault("INITIAL_WRITE_METHOD", "BATCH"));
        DbTransactionMode updatesWriteMethod =
                DbTransactionMode.valueOf(System.getenv().getOrDefault("UPDATE_WRITE_METHOD", "BATCH"));

        String dbKind = System.getenv("DB_KIND");

        switch (dbKind){
            case "ELASTIC_SEARCH":
                return new ElasticSearchApiWriter(numOfEntities, numOfUpdates, initialWriteMethod, updatesWriteMethod);
            default:
                return null;
        }
    }
}
