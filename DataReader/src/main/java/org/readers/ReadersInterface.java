package org.readers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component("ReadersInterface")
public interface ReadersInterface extends JpaRepository<DbModel, String> {

}
