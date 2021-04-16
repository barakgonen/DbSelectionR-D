package org.common.structs;

public class UpdateDataResponse extends BaseRequestParams {
    private UpdateDataRequest originReq;

    public UpdateDataResponse() {
        super();
    }

    public UpdateDataResponse(long startTimeMillis, long endTimeMillis, UpdateDataRequest originReq) {
        super(startTimeMillis, endTimeMillis, originReq.getWritingMethod());
        this.originReq = originReq;
    }

    public int getNumberOfUpdates() {
        return originReq.getNumberOfUpdates();
    }

    public long getTotalDuration() {
        return totalDurationMillis;
    }

    @Override
    public String toString(){
        return "Orig. Req: " + originReq + " " + super.toString();
    }
}
