package org.readers;

import org.springframework.data.util.Pair;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

public class DataReaderController {
    public static Pair<Collection<DbModel>, Long> handleWrites(int numberOfInstances) {
        AtomicLong totalWriteTime = new AtomicLong();
        GenericDbReader dbReader = DataReadersControllerFactory.getController();
        Collection<DbModel> values = dbReader.readFromDb(null, 2333.3);
        return Pair.of(values, 2323L);
    }
}
