package org.common.structs;

import org.joda.time.DateTime;

public class StartSimulationResponse extends BaseRequestParams {
    private StartSimulationRequest request;
    private WritingMethod writingMethod;

    public StartSimulationResponse(StartSimulationRequest request, long startTime, long endTime, WritingMethod writingMethod) {
        super(startTime, endTime);
        this.request = request;
        this.writingMethod = writingMethod;
    }

    public StartSimulationResponse() {
    }
}
