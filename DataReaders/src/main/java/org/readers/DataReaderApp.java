package org.readers;

import org.bg.avro.structures.base.objects.Coordinate;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class DataReaderApp {
    @GetMapping("/query")
    public Long query() {
        DateTime startTime = DateTime.now();
        GenericDbReader reader = DataReadersControllerFactory.getController();
        Collection<DbModel> values = reader.readFromDb(Coordinate.newBuilder().setLat(32).setLon(32.2).setAltitude(23.2).build(), 4.9);

        return DateTime.now().getMillis() - startTime.getMillis();
    }
}