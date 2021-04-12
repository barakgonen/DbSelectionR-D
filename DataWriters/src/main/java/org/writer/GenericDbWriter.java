package org.writer;

import java.util.Collection;

public abstract class GenericDbWriter<SourceSchema, DbSchema> {

    protected DbSchema sourceSchemaToDbSchema() {
        return null;
    }

    /**
     * This is specific implementation of each DB writer
     * returning the timestamp in millis took to write to DB
     * @param object
     */
    protected abstract long writeToDb(DbSchema object);


    /**
     * Test if DB mechanism supports batching
     * @param batchOfUpdates
     */
    protected abstract void writeBatchToDb(Collection<DbSchema> batchOfUpdates);
}
