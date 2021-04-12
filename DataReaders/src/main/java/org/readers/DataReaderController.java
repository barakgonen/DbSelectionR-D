package org.readers;

import jdk.internal.net.http.common.Pair;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

public class DataReaderController {
    public static Pair<Collection<DbModel>, Long> handleWrites(int numberOfInstances) {
        AtomicLong totalWriteTime = new AtomicLong();
        GenericDbReader dbReader = DataReadersControllerFactory.getController();
        Collection<DbModel> values = dbReader.readFromDb(null, 2333.3);
        return new Pair(values, 2323);
    }
}
