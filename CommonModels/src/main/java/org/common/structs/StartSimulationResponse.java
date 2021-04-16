package org.common.structs;

public class StartSimulationResponse extends BaseRequestParams {
    private StartSimulationRequest request;

    public StartSimulationResponse(StartSimulationRequest request, long startTime, long endTime, DbTransactionMode writingMethod) {
        super(startTime, endTime, writingMethod);
        this.request = request;
    }

    public StartSimulationResponse() {
    }

    public int getNumberOfEntities(){
        return request.getNumberOfEntities();
    }

    public long getInsertionDuration() {
        return totalDurationMillis;
    }

    public String toString() {
        return "Orig. Req: " + request + ", " + super.toString();
    }
}
