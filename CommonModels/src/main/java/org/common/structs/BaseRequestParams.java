package org.common.structs;

public class BaseRequestParams {
    protected long startTimeMillis;
    protected long endTimeMillis;
    protected long totalDurationMillis;

    public BaseRequestParams(){

    }

    public BaseRequestParams(long startTimeMillis, long endTimeMillis) {
        this.startTimeMillis = startTimeMillis;
        this.endTimeMillis = endTimeMillis;
        this.totalDurationMillis = this.endTimeMillis - this.startTimeMillis;
    }
}
