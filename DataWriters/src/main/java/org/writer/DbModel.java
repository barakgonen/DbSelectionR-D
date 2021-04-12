package org.writer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.*;
import org.n52.jackson.datatype.jts.GeometryDeserializer;
import org.n52.jackson.datatype.jts.GeometrySerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "posToId")
public class DbModel implements Serializable {
    @Id
    private String uuid;
    @Column(name = "pos")
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
    private Point geom;

    public DbModel(String uui){
        this.uuid = uui;
//        geom = new Point();
    }
    public void setLat(double lat){
        geom.getCoordinate().setX(lat);
    }

    public void setLon(double lon){
        geom.getCoordinate().setY(lon);
    }


}
