package spring.data.es.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
//
//        System.out.println("BGBG");
//        Entity e = new Entity();
//        Config s = new Config();
//        ElasticsearchOperations ss = s.elasticsearchTemplate();
//
//        if (!ss.indexOps(Entity.class).exists())
//            ss.indexOps(Entity.class).create();
//
//        r.save(e);
    }
}
