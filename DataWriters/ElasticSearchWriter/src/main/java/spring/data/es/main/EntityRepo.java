package spring.data.es.main;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepo extends ElasticsearchRepository<ElasticSpecificEntity, String> {
}