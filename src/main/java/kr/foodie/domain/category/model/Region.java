package kr.foodie.domain.category.model;

import lombok.Data;
import javax.persistence.*;


@Entity
@Table(name = "REGION")
@Data
public class Region {

    @Id
    @Column(name = "REGION_ID")
    private Long regionId;

    @Column(name = "PROVINCE_NAME")
    private String provinceName;

    @Column(name = "DISTRICT_NAME")
    private String districtName;

    @Column(name = "SUBWAY_NAME")
    private String subwayName;

    @Column(name = "REGION_TYPE")
    private String regionType;
}
