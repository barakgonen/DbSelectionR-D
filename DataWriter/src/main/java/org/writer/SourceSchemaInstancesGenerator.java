package org.writer;

import org.bg.avro.structures.base.objects.Coordinate;
import org.bg.avro.structures.base.objects.CoordinateWithId;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

public class SourceSchemaInstancesGenerator {

    /**
     * This function generates single instance of server's schema objects which represents single
     * update to be stored
     * @param numberOfInstances - number of elements to generate
     * @return
     */
    public static Collection<DbModel> generateInstances(int numberOfInstances){
        ArrayList<DbModel> generatedInstances = new ArrayList<>();

        for (int i = 0; i < numberOfInstances; i++){
//          generatedInstances.add(CoordinateWithId.newBuilder()
//                                        .setPosition(Coordinate.newBuilder()
//                                                .setAltitude(getRandomDoubleValue())
//                                                .setLon(getRandomDoubleValue())
//                                                .setLat(getRandomDoubleValue())
//                                                .build())
//                                        .setId(UUID.randomUUID().toString())
//                                        .build());
            Point pos = new Point();
            pos.setLocation(getRandomDoubleValue(), getRandomDoubleValue());
            generatedInstances.add(new DbModel(UUID.randomUUID().toString(), pos));
        }
        return generatedInstances;
    }

    private static double getRandomDoubleValue(){
        double min = 1D;
        double max = 10D;
        return getRandomDoubleValueInRange(min, max);
    }

    private static double getRandomDoubleValueInRange(double min, double max){
        return min + new Random().nextDouble() * (max - min);
    }
}
