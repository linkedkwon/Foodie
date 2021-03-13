package kr.foodie.domain.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//'맛집촌'
@Entity
@Table(name = "STREET")
public class Street {

    @Id
    @Column(name = "STREET_ID")
    private long ld;

    @Column(name = "STREET_NAME")
    private String streetName;

    @Column(name = "STREET_COUNT")
    private String count;

    //mapping to meta-detail
}
