package org.common.structs;

import org.springframework.data.util.Pair;

import java.util.Random;
import java.util.UUID;

public class ClientState {
    private String uuid;
    private double lat;
    private double xlon;
    private double radiusInKm;

    public ClientState(int clientIndex) {
        this.uuid = UUID.randomUUID().toString();
        Pair<Pair<Double, Double>, Double> clientData = generateInitialPosition(clientIndex);
        this.lat = clientData.getFirst().getFirst();
        this.xlon = clientData.getFirst().getSecond();
        this.radiusInKm = clientData.getSecond();
    }

    public ClientState(){

    }

    public String getUuid() {
        return uuid;
    }

    public double getLat() {
        return lat;
    }

    public double getXlon() {
        return xlon;
    }

    public double getRadiusInKm() {
        return radiusInKm;
    }

    private void updateZoom() {
        radiusInKm *= Math.random();
    }

    private void updateLocation(){

    }

    private Pair<Pair<Double, Double>, Double> generateInitialPosition(int clientIndex) {

        if (clientIndex % 2 == 0) {
            return Pair.of(generateAroundA(), 32.2 * clientIndex);
        } else if (clientIndex % 3 == 0) {
            return Pair.of(generateAroundB(), 211.2 * clientIndex);
        } else {
            return Pair.of(generateAroundC(), clientIndex * 3.2);
        }

    }
    public void updateClientView() {
        updateZoom();
        updateLocation();
    }

    private Pair<Double, Double> generateAroundA() {
        return generateInRange(10, 20, 10.0, 20);
    }

    private Pair<Double, Double> generateAroundB() {
        return generateInRange(22, 33, 22, 33);
    }

    private Pair<Double, Double> generateAroundC() {
        return generateInRange(45, 50, 45, 50);
    }

    private Pair<Double, Double> generateInRange(double minLat, double maxLat, double minLon, double MaxLon) {
        double lat = getRandomDoubleValueInRange(minLat, maxLat);
        double lon = getRandomDoubleValueInRange(minLon, MaxLon);

        return Pair.of(lat, lon);
    }

    protected double getRandomDoubleValueInRange(double min, double max) {
        return min + new Random().nextDouble() * (max - min);
    }
}
