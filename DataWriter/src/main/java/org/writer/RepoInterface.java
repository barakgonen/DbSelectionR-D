package org.writer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component("RepoInterface")
public interface RepoInterface extends JpaRepository<DbModel, String> {

}
