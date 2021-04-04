package org.writer;

import javax.persistence.*;
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

    public DbModel(String uuid, Point pos) {
        this.uuid = uuid;
        this.pos = pos;
    }
}
