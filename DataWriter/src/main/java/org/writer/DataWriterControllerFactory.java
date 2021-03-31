package org.writer;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.stereotype.Service;
import org.writer.dbWriters.PostgresSqlDbWriter;

@Service
public class DataWriterControllerFactory {
    public static GenericDbWriter getController() {
        switch (System.getenv("TESTING_DB")){
            case "POSTGRES_SQL":
                PostgresSqlDbWriter writer = new PostgresSqlDbWriter();
                AutowireCapableBeanFactory factory = SpringApplicationContext.getContext().getAutowireCapableBeanFactory();
                factory.autowireBean(writer);
                factory.initializeBean(writer, "postgresWriter");

                return writer;
            default:
                System.out.println("Couldnt find db for you returning null");
                return null;
        }
    }
}
