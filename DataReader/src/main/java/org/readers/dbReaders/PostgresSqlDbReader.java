package org.readers.dbReaders;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.util.GeometricShapeFactory;
import jdk.internal.net.http.common.Pair;
import org.postgis.Geometry;
import org.readers.DbModel;
import org.readers.GenericDbReader;
import org.readers.ReadersInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
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
        shapeFactory.setCentre(new Coordinate(usersPointOfView.getLat(), usersPointOfView.getLon()));
        shapeFactory.setSize(radiusInKm * 2);
        Geometry

        return repoInterface.findWithin("", );
    }

//    @Override
//    protected HashMap<String, Collection<DbModel>> readMultiClients(HashMap<String,
//            Pair<Coordinate, Double>> usersPref) {
//        return (HashMap<String, Collection<DbModel>>) repoInterface.findAll();
//    }
}
