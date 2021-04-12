package org.readers;

import org.bg.avro.structures.base.objects.Coordinate;
import org.springframework.data.util.Pair;

import java.util.Collection;
import java.util.HashMap;

public abstract class GenericDbReader<DbSchema> {

    protected DbSchema dbSchemaToSource() {
        return null;
    }

    /**
     * Read collection of data from DB
     * @param usersPointOfView screen center
     * @param radiusInKm radius
     * @return collection of objects
     */
    protected abstract Collection<DbSchema> readFromDb(Coordinate usersPointOfView, double radiusInKm);

    /**
     * Reading once for all clients
     * @param usersPref id <-> position to look for
     * @return collection of io <-> object
     */
    protected abstract HashMap<String, Collection<DbSchema>> readMultiClients
            (HashMap<String, Pair<Coordinate, Double>> usersPref);

}
