package kr.foodie.domain.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STATION")
public class Station {

    @Id
    @Column(name = "STATION_ID")
    private long id;

    @Column(name = "STATION_NAME")
    private String stationName;

    /**
    @Column
    private Subway subway;
    */
}
