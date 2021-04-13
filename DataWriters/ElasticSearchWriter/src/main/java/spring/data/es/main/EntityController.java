package spring.data.es.main;

import org.common.structs.DistGroup;
import org.common.structs.KinematicType;
import org.common.structs.SimulationRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
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
    public ResponseEntity postEntities(@RequestBody SimulationRequest req) {
        if (indexCreatedSuccessfully()) {
            ArrayList<ElasticSpecificEntity> entitiesToGenerate = new ArrayList<>();
            for (int i = 0; i < req.getNumberOfEntities(); i++) {
                if (i % 2 == 0) {
                    entitiesToGenerate.add(new ElasticSpecificEntity("A", KinematicType.POINT, DistGroup.A));
                } else if (i % 3 == 0) {
                    entitiesToGenerate.add(new ElasticSpecificEntity("B", KinematicType.POINT, DistGroup.A));
                } else {
                    entitiesToGenerate.add(new ElasticSpecificEntity("C", KinematicType.POINT, DistGroup.A));
                }
            }

            repo.saveAll(entitiesToGenerate);

            if (req.getNumberOfUpdates() > 1) {
                for (int updateNumber = 0; updateNumber < req.getNumberOfUpdates(); updateNumber++) {
                    entitiesToGenerate.forEach(ElasticSpecificEntity::updatePosition);
                    repo.save(entitiesToGenerate.get(updateNumber));
                }
            }

            return ResponseEntity.ok("finished");
        }
        return ResponseEntity.badRequest().build();
    }
}