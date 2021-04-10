package org.readers;

import org.postgis.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component("ReadersInterface")
public interface ReadersInterface extends JpaRepository<DbModel, String> {

    @Query("SELECT uuid FROM pos_to_id AS l WHERE l.uuid = :l AND within(l.geometry, :filter) = TRUE")
    List<UUID> findWithin(@Param("user") String user, @Param("filter") Geometry filter);
}
