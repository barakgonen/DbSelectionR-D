package spring.data.es.main;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import spring.data.es.main.Entity;

@Repository
public interface EntityRepo extends ElasticsearchRepository<Entity, String> {
}