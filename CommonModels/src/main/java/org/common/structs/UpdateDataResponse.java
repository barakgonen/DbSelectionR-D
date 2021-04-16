package org.common.structs;

import org.joda.time.DateTime;

public class UpdateDataResponse extends BaseRequestParams {
    private UpdateDataRequest originReq;

    public UpdateDataResponse() {
        super();
    }

    public UpdateDataResponse(long startTimeMillis, long endTimeMillis, UpdateDataRequest originReq) {
        super(startTimeMillis, endTimeMillis);
        this.originReq = originReq;
    }
}
