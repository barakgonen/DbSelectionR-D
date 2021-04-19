package org.analysis.db.api;

import org.analysis.db.api.elasticsearch.ElasticSearchApiReader;
import org.infra.IGenericDbReader;

public class DbReaderApiFactory {
    private DbReaderApiFactory(){

    }

    public static IGenericDbReader getDbReaderApi(){
        switch (System.getenv("DB_KIND")){
            case "ELASTIC_SEARCH":
                return new ElasticSearchApiReader();
            case "POSTGRES_SQL":
                return null;
            default:
                return null;
        }
    }
}
