package org.infra;

import org.common.structs.StartSimulationResponse;
import org.common.structs.UpdateDataResponse;

public interface IGenericDbWriter {
    StartSimulationResponse insertToDb();

    UpdateDataResponse periodicUpdates();
}
