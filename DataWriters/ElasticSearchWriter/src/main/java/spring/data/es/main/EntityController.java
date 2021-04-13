package spring.data.es.main;

import org.common.structs.DistGroup;
import org.common.structs.KinematicType;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Service
@RestController
public class EntityController {

    @Autowired
    private EntityRepo repo;
    private Config config = new Config();
    private long queryTime = 0;
    private long queries = 0;

    private boolean indexCreatedSuccessfully() {
        String indexName = "locationwithdata";
        IndexCoordinates coordinates = IndexCoordinates.of(indexName);
        ElasticsearchOperations ss = config.elasticsearchTemplate();

        if (ss.indexOps(coordinates).exists()) {
            ss.indexOps(coordinates).delete();
        }
        try {
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            Map<String, Object> jsonMap = new HashMap<>();
            {
                Map<String, Object> properties = new HashMap<>();
                {
                    Map<String, Object> pin = new HashMap<>();
                    pin.put("type", "geo_point");
                    properties.put("position", pin);
                }
                jsonMap.put("properties", properties);
            }
            request.mapping(jsonMap);
            CreateIndexResponse res = config.getClient().indices().create(request, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/entities")
    public ResponseEntity postEntities(@RequestParam Optional<String> numOfInstances, @RequestParam Optional<String> numOfUpdates) {
        if (indexCreatedSuccessfully()) {
            ArrayList<ElasticSpecificEntity> entitiesToGenerate = new ArrayList<>();
            for (int i = 0; i < Integer.parseInt(numOfInstances.get()); i++) {
                if (i % 2 == 0) {
                    entitiesToGenerate.add(new ElasticSpecificEntity("A", KinematicType.POINT, DistGroup.A));
                } else if (i % 3 == 0) {
                    entitiesToGenerate.add(new ElasticSpecificEntity("B", KinematicType.POINT, DistGroup.A));
                } else {
                    entitiesToGenerate.add(new ElasticSpecificEntity("C", KinematicType.POINT, DistGroup.A));
                }
            }

            repo.saveAll(entitiesToGenerate);

            if (Integer.parseInt(numOfUpdates.get()) > 1) {
                for (int updateNumber = 0; updateNumber < Integer.parseInt(numOfUpdates.get()); updateNumber++) {
                    entitiesToGenerate.forEach(ElasticSpecificEntity::updatePosition);
                    repo.saveAll(entitiesToGenerate);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            return ResponseEntity.ok("finished");
        }
        return ResponseEntity.badRequest().build();
    }


    private GeoDistanceQueryBuilder buildQuery(double distance, double originLat, double originLon, String queryName) {
        return new GeoDistanceQueryBuilder("position")
                .distance(distance, DistanceUnit.KILOMETERS)
                .point(originLat, originLon).queryName(queryName);
    }

    @GetMapping("/entities")
    public ResponseEntity getEntities() {
        queries++;
        DateTime now = DateTime.now();
        ElasticsearchOperations ss = config.elasticsearchTemplate();

        String indexName = "locationwithdata";

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
        String[] name = new String[1];
        name[0] = indexName;
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
        double avg = queryTime / queries;
        return ResponseEntity.ok(avg);
    }
    }
