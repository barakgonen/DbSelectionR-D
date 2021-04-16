package org.elasticsearch.app;

import com.google.gson.Gson;
import org.common.structs.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RestController
public class EntityController {
    // TODO: don't remove index every time
    @Autowired
    private EntityRepo repo;
    private Config config = new Config();
    private long queryTime = 0;
    private long queries = 0;
    private static Gson GSON = new Gson();

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

    @PostMapping("/createEntities")
    public ResponseEntity postEntities(@RequestBody StartSimulationRequest req) {
        if (indexCreatedSuccessfully()) {
            DateTime startTime = new DateTime();
            ArrayList<ElasticSpecificEntity> entitiesToGenerate = new ArrayList<>();
            for (int i = 0; i < req.getNumberOfEntities(); i++) {
                if (i % 2 == 0) {
                    entitiesToGenerate.add(new ElasticSpecificEntity("A"/*, KinematicType.POINT, DistGroup.A*/));
                } else if (i % 3 == 0) {
                    entitiesToGenerate.add(new ElasticSpecificEntity("B"/*, KinematicType.POINT, DistGroup.A*/));
                } else {
                    entitiesToGenerate.add(new ElasticSpecificEntity("C"/*, KinematicType.POINT, DistGroup.A*/));
                }
            }

            if (req.getTransactionMode().equals(DbTransactionMode.BATCH)) {
                repo.saveAll(entitiesToGenerate);
            } else {
                entitiesToGenerate.forEach(repo::save);
            }

            StartSimulationResponse l = new StartSimulationResponse(req, startTime.getMillis(), DateTime.now().getMillis(), DbTransactionMode.BATCH);
            return ResponseEntity.ok(GSON.toJson(l));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/updateData")
    public ResponseEntity postEntities(@RequestBody UpdateDataRequest req) {
        ArrayList<ElasticSpecificEntity> entitiesToGenerate = new ArrayList<>();
        repo.findAll().forEach(entitiesToGenerate::add);

        DateTime startTime = new DateTime();
        if (req.getNumberOfUpdates() > 1) {
            if (req.getWritingMethod().equals(DbTransactionMode.ONE_BY_ONE)) {
                updateOneByOne(req, entitiesToGenerate);
            } else {
                batchUpdate(req, entitiesToGenerate);
            }
        }
        UpdateDataResponse l = new UpdateDataResponse(startTime.getMillis(), DateTime.now().getMillis(), req);
        return ResponseEntity.ok(GSON.toJson(l));
    }

    private void updateOneByOne(UpdateDataRequest req, ArrayList<ElasticSpecificEntity> entitiesToGenerate) {
        for (int updateNumber = 0; updateNumber < req.getNumberOfUpdates(); updateNumber++) {
            for (ElasticSpecificEntity entity : entitiesToGenerate) {
                entity.updatePosition();
                repo.save(entity);
            }
        }
    }

    private void batchUpdate(UpdateDataRequest req, ArrayList<ElasticSpecificEntity> entitiesToGenerate) {
        for (int updateNumber = 0; updateNumber < req.getNumberOfUpdates(); updateNumber++) {
            for (ElasticSpecificEntity entity : entitiesToGenerate) {
                entity.updatePosition();
            }
            repo.saveAll(entitiesToGenerate);
        }
    }
}