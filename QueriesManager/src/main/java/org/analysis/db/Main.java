package org.analysis.db;

import org.analysis.db.api.DbWriterApiFactory;
import org.common.structs.DbReadRequest;
import org.common.structs.DbReadResponse;
import org.common.structs.StartSimulationResponse;
import org.common.structs.UpdateDataResponse;
import org.infra.IGenericDbWriter;
import org.springframework.data.util.Pair;

import java.io.BufferedWriter;
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

        int NUMBER_OF_THREADS = Integer.parseInt(System.getenv().getOrDefault("NUMBER_OF_THREADS", "10"));
        int NUMBER_OF_REPEATS = Integer.parseInt(System.getenv().getOrDefault("NUMBER_OF_REPEATS", "20"));

        ExecutorService service = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        LinkedList<ResultsAnalayzer> analyzedResults = new LinkedList<>();
        HashMap<Integer, Integer> entitiesToUpdates = new HashMap<>();
//        entitiesToUpdates.put(10, 5);
//        entitiesToUpdates.put(100, 100);
//        entitiesToUpdates.put(500, 100);
//        entitiesToUpdates.put(1000, 100);
//        entitiesToUpdates.put(2500, 1000);
//        entitiesToUpdates.put(5000, 2000);
//        entitiesToUpdates.put(7500, 50);
        entitiesToUpdates.put(10000, 50);
//        entitiesToUpdates.put(20000, 50);
//        entitiesToUpdates.put(30000, 50);
//        entitiesToUpdates.put(40000, 50);
//        entitiesToUpdates.put(50000, 50);
//        entitiesToUpdates.put(60000, 50);
//        entitiesToUpdates.put(70000, 50);
//        entitiesToUpdates.put(80000, 50);
//        entitiesToUpdates.put(90000, 50);
//        entitiesToUpdates.put(100000, 50);

        entitiesToUpdates.forEach((numOfEntities, numOfUpdates) -> {
            IGenericDbWriter dbWriter = DbWriterApiFactory.getDbReaderApi(numOfEntities, numOfUpdates);
            for (int i = 0; i < NUMBER_OF_REPEATS; i++) {
                analyzedResults.add(runTest(service, dbWriter));
            }
        });

        exportResultsToCsv("bgTest", analyzedResults);
        service.shutdown();
    }

    private static void exportResultsToCsv(String fileName, LinkedList<ResultsAnalayzer> analyzedResults) {
        // Open file for writing

        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter("/local/" + fileName + ".csv"));
            // Writing header
            ArrayList<String> linesToWrite = new ArrayList<>();
            linesToWrite.add("Num Of Entities,Insertion Transaction Mode,Insertion Duration(ms),Num of Updates,Updates Transaction Mode,Updates duration(ms)\n");
            analyzedResults.forEach(resultsAnalayzer -> linesToWrite.add(resultsAnalayzer.toCsv()));
            for (String s : linesToWrite) {
                writer.write(s);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ResultsAnalayzer runTest(ExecutorService service, IGenericDbWriter elasticSearchApiWriter) {
        Future<Pair<StartSimulationResponse, UpdateDataResponse>> writerService =
                service.submit(new DbWriter(elasticSearchApiWriter));

        Future<DbReadResponse> readerService =
                service.submit(new DbReader(new DbReadRequest(100, 100)));

        while (!readerService.isDone() || !writerService.isDone()){
//            System.out.println("BG");
        }
        try {
            Pair<Pair<StartSimulationResponse, UpdateDataResponse>, DbReadResponse> response =
                    Pair.of(writerService.get(), readerService.get());
            System.out.println("=====================================================================================");
            System.out.println("| Creation: ");
            System.out.println("| " + response.getFirst().getFirst());
            System.out.println("| ---------------------------------------------------------------------------------- ");
            System.out.println("| Updates: ");
            System.out.println("| " + response.getFirst().getSecond());
            System.out.println("=====================================================================================");
            System.out.println("| ---------------------------------------------------------------------------------- ");
            System.out.println("| Read: ");
            System.out.println("| " + response.getSecond());
            System.out.println("=====================================================================================");
            return new ResultsAnalayzer(response.getFirst().getFirst(), response.getFirst().getSecond(), readerService.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
