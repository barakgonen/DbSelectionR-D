package org.readers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.geo.Point;

@Entity
@Table(name = "posToId")
public class DbModel {
    @Id
    private String uuid;

    @Column(name = "pos")
    private Point pos;

    public DbModel(){

    }

    @Override
    public String toString() {
        return "DbModel{" +
                "uuid='" + uuid + '\'' +
                ", pos=" + pos +
                '}';
    }
}
