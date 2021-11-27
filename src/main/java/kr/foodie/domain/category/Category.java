package kr.foodie.domain.category;

import lombok.Data;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "REGION")
@Data
public class Category {

    @Id
    @Column(name = "REGION_ID")
    @GeneratedValue(strategy = IDENTITY)
    private Long regionId;

    @Column(name = "PROVINCE_NAME")
    private String provinceName;

    @Column(name = "DISTRICT_NAME")
    private String districtName;

    @Column(name = "SUBWAY_NAME")
    private String subwayName;

    @Column(name = "REGION_TYPE")
    private String regionType;

    @Column(name = "DISTRICT_COUNT")
    private Integer districtCnt;
}
