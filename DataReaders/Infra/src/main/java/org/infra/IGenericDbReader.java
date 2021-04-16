package org.infra;

import org.common.structs.DbReadRequest;
import org.common.structs.DbReadResponse;

public interface IGenericDbReader {
    DbReadResponse queryDb(DbReadRequest request);
}
