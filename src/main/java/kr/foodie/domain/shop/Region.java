package kr.foodie.domain.shop;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "REGION")
@Data
public class Region {

    @Id
    @Column(name = "REGION_ID")
    private Integer regionId;

    @Column(name = "PROVINCE_NAME")
    private String provinceName;

    @Column(name = "DISTRICT_NAME")
    private String districtName;

    @Column(name = "REGION_TYPE")
    private String regionType;

    @Column(name = "SUBWAY_NAME")
    private String subwayName;

    @Column(name = "DISTRICT_COUNT")
    private Integer districtCnt;
}
