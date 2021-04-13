package org.common.structs;

import java.util.Random;
import java.util.UUID;

public abstract class AbstractEntity {
    protected String id;
    protected String area;
//    protected KinematicType kinematicType;
//    protected DistGroup distGroup;

    protected double getRandomDoubleValueInRange(double min, double max) {
        return min + new Random().nextDouble() * (max - min);
    }

    protected AbstractEntity(String area/*, KinematicType kinematicType, DistGroup distGroup*/){
        this.id = UUID.randomUUID().toString();
        this.area = area;
//        this.kinematicType = kinematicType;
//        this.distGroup = distGroup;
    }

    public abstract void updatePosition();
}
