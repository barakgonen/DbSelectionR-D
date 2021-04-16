package main;

import org.common.structs.DbReadRequest;
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
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

@Service
@RestController
public class EntityController {

    @Autowired
    private Config config = new Config();
    private long queryTime = 0;
    private long queries = 0;

    private GeoDistanceQueryBuilder buildQuery(double distance, double originLat, double originLon, String queryName) {
        return new GeoDistanceQueryBuilder("position")
                .distance(distance, DistanceUnit.KILOMETERS)
                .point(originLat, originLon).queryName(queryName);
    }

    @PostMapping("/entities")
    public ResponseEntity getEntities(@RequestBody DbReadRequest readRequest) {
        queries++;
        DateTime now = DateTime.now();
        String indexName = "locationwithdata";
        String[] name = new String[1];
        name[0] = indexName;


        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder =
                new BoolQueryBuilder()
                        .should(buildQuery(10, 18.4958830, 15.303165601, "10KM from A"))
                        .should(buildQuery(44, 31.7885617, 23.1816748, "44 km from B"))
                        .should(buildQuery(100, 18.4958830, 15.303165601, "100KM from A"))
                        .should(buildQuery(1, 48.502517, 49.431812, "1 km from C"))
                        .should(buildQuery(4, 31.7885617, 23.1816748, "4 km from B"))
                        .should(buildQuery(55, 48.523, 49.4, "55 km from C"));
        sourceBuilder.query(boolQueryBuilder);
        SearchRequest requestt = new SearchRequest(name, sourceBuilder);

        HashMap<String, LinkedList<String>> areaToIds = new HashMap<>();

        try {
            SearchResponse res = config.getClient().search(requestt, RequestOptions.DEFAULT);
            SearchHit[] hits = res.getHits().getHits();

            for (SearchHit hit : hits) {
                for (String query : hit.getMatchedQueries()) {
                    if (areaToIds.containsKey(query))
                        areaToIds.get(query).add(hit.getId());
                    else
                        areaToIds.put(query, new LinkedList(Collections.singleton(hit.getId())));
                }
            }
            queryTime += (DateTime.now().getMillis() - now.getMillis());
            return ResponseEntity.ok(areaToIds.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/avg")
    public ResponseEntity getAvg() {
        float avg = queryTime / queries;
        return ResponseEntity.ok(avg);
    }
}
