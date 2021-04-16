package org.common.structs;


import org.springframework.data.util.Pair;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class DbReadRequest {
    private HashMap<String, Pair<Point, Double>> clientsZoom;

    public DbReadRequest(int numberOfClients) {
        clientsZoom = new HashMap<>();
        initializeClientsView(numberOfClients);
    }

    public DbReadRequest(){

    }

    private void initializeClientsView(int numberOfClients) {

        for (int i = 0; i < numberOfClients; i++) {
            if (i % 2 == 0) {
                clientsZoom.put(UUID.randomUUID().toString(), Pair.of(generateAroundA(), 32.2 * i));
            } else if (i % 3 == 0) {
                clientsZoom.put(UUID.randomUUID().toString(), Pair.of(generateAroundB(), 211.2 * i));
            } else {
                clientsZoom.put(UUID.randomUUID().toString(), Pair.of(generateAroundC(), i * 3.2));
            }
        }

    }

    public void updateClientsView() {
        // TODO do something
    }

    private Point generateAroundA() {
        return generateInRange(10, 20, 10.0, 20);
    }

    private Point generateAroundB() {
        return generateInRange(22, 33, 22, 33);
    }

    private Point generateAroundC() {
        return generateInRange(45, 50, 45, 50);
    }

    private Point generateInRange(double minLat, double maxLat, double minLon, double MaxLon) {
        double lat = getRandomDoubleValueInRange(minLat, maxLat);
        double lon = getRandomDoubleValueInRange(minLon, MaxLon);

        return new Point(5 , 5);
    }

    protected double getRandomDoubleValueInRange(double min, double max) {
        return min + new Random().nextDouble() * (max - min);
    }
}
