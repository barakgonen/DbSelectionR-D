package org.readers.dbReaders;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.util.GeometricShapeFactory;
import org.readers.DbModel;
import org.readers.GenericDbReader;
import org.readers.ReadersInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

@Component
public class PostgresSqlDbReader extends GenericDbReader<DbModel> {
    @Autowired
    @Qualifier("ReadersInterface")
    ReadersInterface repoInterface;

    @Override
    protected Collection<DbModel> readFromDb(org.bg.avro.structures.base.objects.Coordinate usersPointOfView, double radiusInKm) {
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
        shapeFactory.setNumPoints(32);
        shapeFactory.setCentre(new Coordinate(usersPointOfView.getLatitude(), usersPointOfView.getLongitude()));
        shapeFactory.setSize(radiusInKm * 2);
//        Geometry

//        return repoInterface.findWithin("", );
        // TODO fixme
        return Collections.emptyList();
    }

    @Override
    protected HashMap<String, Collection<DbModel>> readMultiClients(HashMap<String, Pair<org.bg.avro.structures.base.objects.Coordinate, Double>> usersPref) {
        return null;
    }

//    @Override
//    protected HashMap<String, Collection<DbModel>> readMultiClients(HashMap<String,
//            Pair<Coordinate, Double>> usersPref) {
//        return (HashMap<String, Collection<DbModel>>) repoInterface.findAll();
//    }
}
