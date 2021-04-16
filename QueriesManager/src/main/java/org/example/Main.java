package org.example;

import org.common.structs.*;
import org.infra.IGenericDbReader;
import org.infra.IGenericDbWriter;
import org.springframework.data.util.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {

        int NUMBER_OF_THREADS = 10;
        int NUMBER_OF_REPEATS = 20;
        ArrayList<String> dbs = new ArrayList<>();
        dbs.add("ElasticSearch");

        ExecutorService service = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        LinkedList<ResultsAnalayzer> analayzedResults = new LinkedList<>();

        HashMap<Integer, Integer> entitiesToUpdates = new HashMap<>();
//        entitiesToUpdates.put(10, 50);
//        entitiesToUpdates.put(100, 100);
//        entitiesToUpdates.put(500, 100);
//        entitiesToUpdates.put(1000, 100);
//        entitiesToUpdates.put(2500, 1000);
//        entitiesToUpdates.put(5000, 2000);
//        entitiesToUpdates.put(7500, 50);
        entitiesToUpdates.put(10000, 50);
        entitiesToUpdates.put(20000, 50);
        entitiesToUpdates.put(30000, 50);
        entitiesToUpdates.put(40000, 50);
        entitiesToUpdates.put(50000, 50);
        entitiesToUpdates.put(60000, 50);
        entitiesToUpdates.put(70000, 50);
        entitiesToUpdates.put(80000, 50);
        entitiesToUpdates.put(90000, 50);
        entitiesToUpdates.put(100000, 50);

        entitiesToUpdates.forEach((numOfEntities, numOfUpdates) -> {
            IGenericDbWriter elasticSearchApiWriter = new ElasticSearchApiWriter(numOfEntities, numOfUpdates, DbTransactionMode.BATCH);
            for (int i = 0; i < NUMBER_OF_REPEATS; i++) {
                analayzedResults.add(runElasticSearchWriterAndReader(service, elasticSearchApiWriter));
            }
        });



        exportResultsToCsv("bgTest", analayzedResults);
        service.shutdown();
        System.out.println("bgbg");

    }

    private static void exportResultsToCsv(String fileName, LinkedList<ResultsAnalayzer> analayzedResults) {
        // Open file for writing

        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter("/local/" + fileName + ".csv"));
            String str = "";
            // Writing header
            ArrayList<String> linesToWrite = new ArrayList<>();
            linesToWrite.add("Num Of Entities,Insertion Transaction Mode,Insertion Duration(ms),Num of Updates,Updates Transaction Mode,Updates duration(ms)\n");
            analayzedResults.forEach(resultsAnalayzer -> linesToWrite.add(resultsAnalayzer.toCsv()));
            for (String s : linesToWrite) {
                writer.write(s);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ResultsAnalayzer runElasticSearchWriterAndReader(ExecutorService service, IGenericDbWriter elasticSearchApiWriter) {
        Future<Pair<StartSimulationResponse, UpdateDataResponse>> writerService =
                service.submit(new DbWriter(elasticSearchApiWriter));
        DbReadRequest readRequest = new DbReadRequest(50);
        IGenericDbReader elasticReader = new ElasticSearchApiReader();
        LinkedList<DbReadResponse> readRequests = new LinkedList<>();
        while (!writerService.isDone()) {
            readRequests.add(elasticReader.queryDb(readRequest));
            readRequest.updateClientsView();
//            System.out.println("still writing, sleeping for: 200 ms");
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        try {
            Pair<StartSimulationResponse, UpdateDataResponse> response = writerService.get();
            System.out.println("=====================================================================================");
            System.out.println("| Creation: ");
            System.out.println("| " + response.getFirst());
            System.out.println("| ---------------------------------------------------------------------------------- ");
            System.out.println("| Updates: ");
            System.out.println("| " + response.getSecond());
            System.out.println("=====================================================================================");
            return new ResultsAnalayzer(response.getFirst(), response.getSecond());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
