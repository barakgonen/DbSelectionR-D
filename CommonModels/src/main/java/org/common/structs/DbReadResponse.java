package org.common.structs;

public class DbReadResponse {
    private DbReadRequest request;
    private long totalDurationMillis;

    public DbReadResponse(DbReadRequest request){
        this.request = request;
    }
}
