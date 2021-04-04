package org.readers;

import org.readers.dbReaders.PostgresSqlDbReader;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

@Service
public class DataReadersControllerFactory {
    public static GenericDbReader getController() {
        switch (System.getenv("TESTING_DB")) {
            case "POSTGRES_SQL":
                PostgresSqlDbReader writer = new PostgresSqlDbReader();
                AutowireCapableBeanFactory factory = SpringApplicationContext.getContext().getAutowireCapableBeanFactory();
                factory.autowireBean(writer);
                factory.initializeBean(writer, "postgresWriter");

                return writer;
            default:
                System.out.println("Couldn't find db for you returning null");
                return null;
        }
    }
}
