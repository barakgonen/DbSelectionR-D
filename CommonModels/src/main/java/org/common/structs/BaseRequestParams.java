package org.common.structs;

public class BaseRequestParams {
    protected long startTimeMillis;
    protected long endTimeMillis;
    protected long totalDurationMillis;
    protected DbTransactionMode dbTransactionMode;

    public BaseRequestParams(){

    }

    public BaseRequestParams(long startTimeMillis, long endTimeMillis, DbTransactionMode dbTransactionMode) {
        this.startTimeMillis = startTimeMillis;
        this.endTimeMillis = endTimeMillis;
        this.totalDurationMillis = this.endTimeMillis - this.startTimeMillis;
        this.dbTransactionMode = dbTransactionMode;
    }

    public DbTransactionMode getDbTransactionMode() {
        return dbTransactionMode;
    }

    @Override
    public String toString() {
        return "Total time millis: " + totalDurationMillis + ", dbTransactionMode: " + dbTransactionMode;
    }
}
