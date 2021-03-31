package org.writer.dbWriters;

import org.bg.avro.structures.base.objects.CoordinateWithId;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.writer.DbModel;
import org.writer.GenericDbWriter;
import org.writer.RepoInterface;

import java.util.Collection;

@Component
public class PostgresSqlDbWriter extends GenericDbWriter<CoordinateWithId, DbModel> {
    @Autowired
    @Qualifier("RepoInterface")
    RepoInterface repoInterface;

    @Override
    protected long writeToDb(DbModel object) {
        DateTime now = DateTime.now();
        System.out.println("Writed to postgressql!");
        repoInterface.save(object);
        return DateTime.now().getMillis() - now.getMillis();
    }

    @Override
    protected void writeBatchToDb(Collection<DbModel> batchOfUpdates) {
        repoInterface.saveAll(batchOfUpdates);
        System.out.println("Writed batch to postgressql");

    }
}
