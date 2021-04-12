package spring.data.es.main;


import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.util.Pair;


import java.util.Random;
import java.util.UUID;

@Document(indexName = "locationwithdata")
public class Entity {
    private String id;
    @GeoPointField
    private GeoPoint position;
    private String area;

    public Entity(String area) {
        id = UUID.randomUUID().toString();
        switch (area){
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
        this.area = area;
    }

    private void setPosition() {
        switch (area){
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

    public void updatePosition(){
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

    private double getRandomDoubleValueInRange(double min, double max) {
        return min + new Random().nextDouble() * (max - min);
    }


}