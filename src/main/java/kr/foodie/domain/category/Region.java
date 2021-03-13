package kr.foodie.domain.category;

import javax.persistence.*;

@Entity
@Table(name = "REGION")
public class Region {

    @Id
    @Column(name = "REGION_ID")
    private long id;

    @Column(name = "REGION_NAME")
    private String regionName;

    /**
    @Column
    private District district;
    */
}
