package spring.data.es.main;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import spring.data.es.main.Entity;

@Repository
public interface EntityRepo extends ElasticsearchRepository<Entity, String> {

//    Page<Entity> findByAuthorsName(String name, Pageable pageable);

//    @Query("{\"bool\": {\"must\": [{\"match\": {\"authors.name\": \"?0\"}}]}}")
//    Page<Entity> findByAuthorsNameUsingCustomQuery(String name, Pageable pageable);
//
//    @Query("{\"bool\": {\"must\": {\"match_all\": {}}, \"filter\": {\"term\": {\"tags\": \"?0\" }}}}")
//    Page<Entity> findByFilteredTagQuery(String tag, Pageable pageable);
//
//    @Query("{\"bool\": {\"must\": {\"match\": {\"authors.name\": \"?0\"}}, \"filter\": {\"term\": {\"tags\": \"?1\" }}}}")
//    Page<Entity> findByAuthorsNameAndFilteredTagQuery(String name, String tag, Pageable pageable);
}