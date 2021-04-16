package elasticsearch.rest.api;

import com.google.gson.Gson;
import elasticsearch.app.ElasticSpecificEntity;
import org.common.structs.StartSimulationRequest;
import org.common.structs.StartSimulationResponse;
import org.common.structs.UpdateDataResponse;
import org.infra.IGenericDbWriter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ElasticSearchApiWriter implements IGenericDbWriter {

    private int numberOfEntities;
    private int numberOfUpdates;
    private static Gson GSON;
    private ArrayList<ElasticSpecificEntity> entities;

    public ElasticSearchApiWriter(int numberOfEntities, int numberOfUpdates) {
        this.numberOfEntities = numberOfEntities;
        this.numberOfUpdates = numberOfUpdates;
        this.entities = new ArrayList<>();
        this.GSON = new Gson();
    }

    @Override
    public StartSimulationResponse insertToDb() {
        StartSimulationRequest r = new StartSimulationRequest(this.numberOfEntities);
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
        return null;
    }
}
