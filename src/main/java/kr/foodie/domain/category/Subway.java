package kr.foodie.domain.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SUBWAY")
public class Subway {

    @Id
    @Column(name = "SUBWAY_ID")
    private long id;

    @Column(name = "SUBWAY_NAME")
    private String subwayName;

    @Column(name = "SUBWAY_COUNT")
    private String count;

    //mapping to meta-detail
}
