package main;

import com.google.gson.Gson;
import org.common.structs.ClientState;
import org.common.structs.DbReadRequest;
import org.common.structs.DbReadResponse;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

@Service
@RestController
public class EntityController {

    @Autowired
    private Config config = new Config();
    private long queryTimeMillis = 0;
    private long queries = 0;
    private long netoQueryTimeMillis = 0;
    private static Gson GSON = new Gson();

    private GeoDistanceQueryBuilder buildQuery(ClientState clientState) {
        return new GeoDistanceQueryBuilder("position")
                .distance(clientState.getRadiusInKm(), DistanceUnit.KILOMETERS)
                .point(clientState.getLat(), clientState.getXlon()).queryName(clientState.getUuid());
    }

    @PostMapping("/readFromDb")
    public ResponseEntity getEntities(@RequestBody DbReadRequest readRequest) {
        queryTimeMillis = 0;
        queries = 0;
        netoQueryTimeMillis = 0;
        String indexName = "locationwithdata";
        String[] name = new String[1];
        name[0] = indexName;

        // Generate clients according to request
        ArrayList<ClientState> clientStates = new ArrayList<>();
        for (int i = 1; i <= readRequest.getNumberOfClients(); i++) {
            clientStates.add(new ClientState(i));
        }

        for (int i = 0; i < readRequest.getNumberOfQueriesForClient(); i++) {
            DateTime now = DateTime.now();

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

            // Building query
            BoolQueryBuilder builder2 = new BoolQueryBuilder();
            for (ClientState client : clientStates
            ) {
                builder2 = builder2.should(buildQuery(client));
            }
            sourceBuilder.query(builder2);
            SearchRequest requestt = new SearchRequest(name, sourceBuilder);

            HashMap<String, LinkedList<String>> areaToIds = new HashMap<>();

            try {
                SearchResponse res = config.getClient().search(requestt, RequestOptions.DEFAULT);
                netoQueryTimeMillis += res.getTook().getMillis();
                SearchHit[] hits = res.getHits().getHits();

                for (SearchHit hit : hits) {
                    for (String query : hit.getMatchedQueries()) {
                        if (areaToIds.containsKey(query))
                            areaToIds.get(query).add(hit.getId());
                        else
                            areaToIds.put(query, new LinkedList(Collections.singleton(hit.getId())));
                    }
                }
                queryTimeMillis += (DateTime.now().getMillis() - now.getMillis());
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (ElasticsearchException e){
                System.out.println("ERROR ELASTIC ERROR");
                i-=1;
            }
            catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.ok(GSON.toJson(new DbReadResponse(readRequest, queryTimeMillis, netoQueryTimeMillis)));

    }

    @GetMapping("/avg")
    public ResponseEntity getAvg() {
        float avg = queryTimeMillis / queries;
        return ResponseEntity.ok(avg);
    }
}
