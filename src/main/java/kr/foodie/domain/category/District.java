package kr.foodie.domain.category;

import javax.persistence.*;

@Entity
@Table(name = "DISTRICT")
public class District {

    @Id
    @Column(name = "DISTRICT_ID")
    private long id;

    @Column(name = "DISTRICT_NAME")
    private String districtName;

    //mapping to meta-detail
}
