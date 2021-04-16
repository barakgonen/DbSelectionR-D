package org.example;

import elasticsearch.rest.api.ElasticSearchApiWriter;
import org.common.structs.StartSimulationResponse;
import org.common.structs.UpdateDataResponse;
import org.infra.IGenericDbWriter;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {

        int NUMBER_OF_THREADS = 10;
        ArrayList<String> dbs = new ArrayList<>();
        dbs.add("ElasticSearch");

        ExecutorService service = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        IGenericDbWriter elasticSearchApiWriter = new ElasticSearchApiWriter(10, 50);
        Future<Pair<StartSimulationResponse, UpdateDataResponse>> writerService =
                service.submit(new DbWriter(elasticSearchApiWriter));

        while (!writerService.isDone()) {
            System.out.println("still writing, sleeping for: 200 ms");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("writing has over!");
        service.shutdown();
        System.out.println("bgbg");

    }
}
