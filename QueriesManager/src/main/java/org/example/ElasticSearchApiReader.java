package org.example;

import com.google.gson.Gson;
import org.common.structs.DbReadRequest;
import org.common.structs.DbReadResponse;
import org.common.structs.StartSimulationResponse;
import org.infra.IGenericDbReader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ElasticSearchApiReader implements IGenericDbReader {

    private final static Gson GSON = new Gson();
    @Override
    public DbReadResponse queryDb(DbReadRequest readRequest) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8086/entities"))
                    .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(readRequest)))
                    .setHeader("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return GSON.fromJson(response.body(), DbReadResponse.class);
            } else {
                return null;
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
