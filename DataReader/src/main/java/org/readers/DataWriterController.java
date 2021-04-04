package org.readers;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

public class DataWriterController {
    public static long handleWrites(int numberOfInstances) {
        AtomicLong totalWriteTime = new AtomicLong();
        Collection<DbModel> coordinates = SourceSchemaInstancesGenerator.generateInstances(numberOfInstances);
        GenericDbReader dbWriter = DataReadersControllerFactory.getController();
        coordinates.forEach(coordinateWithId -> {
            assert dbWriter != null;
            totalWriteTime.addAndGet(dbWriter.writeToDb(coordinateWithId));
        });
        return totalWriteTime.get() / numberOfInstances;
    }
}
