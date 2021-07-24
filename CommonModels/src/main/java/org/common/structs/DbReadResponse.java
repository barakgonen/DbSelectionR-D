package org.common.structs;

public class DbReadResponse {
    private DbReadRequest request;
    private long totalDurationMillis;
    private long dbWorkTime;

    public DbReadResponse(DbReadRequest request, long totalDurationMillis, long dbWorkTime){
        this.request = request;
        this.totalDurationMillis = totalDurationMillis;
        this.dbWorkTime = dbWorkTime;
    }

    public long getDbAvgWorkTime() {
        return dbWorkTime / request.getNumberOfQueriesForClient();
    }
    @Override
    public String toString(){
        return "Origin req: " + request + ", total duration millis: " + totalDurationMillis
                + " total time for db query: " + dbWorkTime
                + " average query tine: " + totalDurationMillis / request.getNumberOfQueriesForClient();
    }
}
