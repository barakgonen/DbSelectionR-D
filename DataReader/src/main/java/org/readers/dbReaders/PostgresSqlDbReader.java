package org.readers.dbReaders;

import jdk.internal.net.http.common.Pair;
import org.bg.avro.structures.base.objects.Coordinate;
import org.readers.DbModel;
import org.readers.GenericDbReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;

@Component
public class PostgresSqlDbReader extends GenericDbReader<DbModel> {
    @Autowired
    @Qualifier("ReadersInterface")
    RepoInterface repoInterface;

    @Override
    protected Collection<DbModel> readFromDb(Coordinate usersPointOfView, double radiusInKm) {
        return null;
    }

    @Override
    protected HashMap<String, Collection<DbModel>> readMultiClients(HashMap<String,
            Pair<Coordinate, Double>> usersPref) {
        return null;
    }
}
