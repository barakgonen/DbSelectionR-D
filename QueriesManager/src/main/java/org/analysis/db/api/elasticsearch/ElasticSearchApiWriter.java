package org.analysis.db.api.elasticsearch;

import com.google.gson.Gson;
import org.common.structs.*;
import org.infra.IGenericDbWriter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ElasticSearchApiWriter implements IGenericDbWriter {

    private int numberOfEntities;
    private int numberOfUpdates;
    private static Gson GSON;
    private DbTransactionMode writingTransactionMode;
    private DbTransactionMode updatesTransactionMode;

    public ElasticSearchApiWriter(int numberOfEntities, int numberOfUpdates,
                                  DbTransactionMode writingTransactionMode, DbTransactionMode updatesTransactionMode) {
        this.numberOfEntities = numberOfEntities;
        this.numberOfUpdates = numberOfUpdates;
        this.GSON = new Gson();
        this.writingTransactionMode = writingTransactionMode;
        this.updatesTransactionMode = updatesTransactionMode;
    }

    public ElasticSearchApiWriter(int numberOfEntities, int numberOfUpdates,
                                  DbTransactionMode dbTransactionsMode) {
        this.numberOfEntities = numberOfEntities;
        this.numberOfUpdates = numberOfUpdates;
        this.GSON = new Gson();
        this.writingTransactionMode = dbTransactionsMode;
        this.updatesTransactionMode = dbTransactionsMode;
    }

    public ElasticSearchApiWriter(int numberOfEntities, DbTransactionMode dbTransactionMode) {
        this(numberOfEntities, 0, dbTransactionMode);
    }

    @Override
    public StartSimulationResponse insertToDb() {
        StartSimulationRequest r = new StartSimulationRequest(this.numberOfEntities, this.writingTransactionMode);
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/createEntities"))
                    .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(r)))
                    .setHeader("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return GSON.fromJson(response.body(), StartSimulationResponse.class);
            } else {
                return null;
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UpdateDataResponse periodicUpdates() {
        UpdateDataRequest updateDataRequest = new UpdateDataRequest(this.numberOfUpdates, this.updatesTransactionMode);
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/updateData"))
                    .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(updateDataRequest)))
                    .setHeader("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return GSON.fromJson(response.body(), UpdateDataResponse.class);
            } else {
                return null;
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
