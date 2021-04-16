package org.elasticsearch.app;

import org.common.structs.AbstractEntity;
import org.common.structs.DistGroup;
import org.common.structs.KinematicType;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

/**
 * This class represents ElasticSearch's entity for our project
 * GeoPoint is Elastic's specific type thus, must be extended from an AbstractEntity
 */
@Document(indexName = "locationwithdata")
public class ElasticSpecificEntity extends AbstractEntity {
    @GeoPointField
    private GeoPoint position;

    public ElasticSpecificEntity(String area/*, KinematicType kinematicType, DistGroup distGroup*/) {
        super(area/*, kinematicType, distGroup*/);
        switch (area) {
            case "A":
                position = new GeoPoint(generateAroundA());
                break;
            case "B":
                position = new GeoPoint(generateAroundB());
                break;
            case "C":
                position = new GeoPoint(generateAroundC());
                break;
        }
    }

    private void setPosition() {
        switch (area) {
            case "A":
                position = new GeoPoint(generateAroundA());
                break;
            case "B":
                position = new GeoPoint(generateAroundB());
                break;
            case "C":
                position = new GeoPoint(generateAroundC());
                break;
        }
    }

    public void updatePosition() {
        setPosition();
    }

    private GeoPoint generateAroundA() {
        return generateInRange(10, 20, 10.0, 20);
    }

    private GeoPoint generateAroundB() {
        return generateInRange(22, 33, 22, 33);
    }

    private GeoPoint generateAroundC() {
        return generateInRange(45, 50, 45, 50);
    }

    private GeoPoint generateInRange(double minLat, double maxLat, double minLon, double MaxLon) {
        double lat = getRandomDoubleValueInRange(minLat, maxLat);
        double lon = getRandomDoubleValueInRange(minLon, MaxLon);

        return new GeoPoint(lat, lon);
    }
}