package org.writer;
import org.locationtech.jts.geom.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

public class SourceSchemaInstancesGenerator {

    /**
     * This function generates single instance of server's schema objects which represents single
     * update to be stored
     *
     * @param numberOfInstances - number of elements to generate
     * @return
     */
    public static Collection<DbModel> generateInstances(int numberOfInstances) {
        ArrayList<DbModel> generatedInstances = new ArrayList<>();

        for (int i = 0; i < numberOfInstances; i++) {
            DbModel b = new DbModel(UUID.randomUUID().toString());
            b.setLat(getRandomDoubleValue());
            b.setLon(getRandomDoubleValue());
            generatedInstances.add(b);
//            Point pos = new Point(getRandomDoubleValue(), getRandomDoubleValue());
//            generatedInstances.add(new DbModel(UUID.randomUUID().toString(), pos));
        }
        return generatedInstances;
    }

    private static double getRandomDoubleValue() {
        double min = 31;
        double max = 37;
        return getRandomDoubleValueInRange(min, max);
    }

    private static double getRandomDoubleValueInRange(double min, double max) {
        return min + new Random().nextDouble() * (max - min);
    }
}
