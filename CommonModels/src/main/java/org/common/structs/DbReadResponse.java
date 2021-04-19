package org.common.structs;

public class DbReadResponse {
    private DbReadRequest request;
    private long totalDurationMillis;

    public DbReadResponse(DbReadRequest request, long totalDurationMillis){
        this.request = request;
        this.totalDurationMillis = totalDurationMillis;
    }

    @Override
    public String toString(){
        return "Origin req: " + request + ", total duration millis: " + totalDurationMillis + " average query tine: " + totalDurationMillis / request.getNumberOfQueriesForClient();
    }
}
