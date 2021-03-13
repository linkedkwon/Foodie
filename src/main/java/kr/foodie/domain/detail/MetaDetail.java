package kr.foodie.domain.detail;

import javax.persistence.*;

@Entity
@Table(name = "META_DETAIL")
public class MetaDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "META_DETAIL_ID")
    private long id;

    @Column(name = "DETAIL_TITLE")
    private String title;

    @Column(name = "DETAIL_CATEGORY")
    private String category;

    @Column(name = "DETAIL_ADDRESS")
    private String address;

    @Column(name = "DETAIL_TEL")
    private String telNum;

    @Column(name = "DETAIL_DESCRIPTION")
    private String description;

    //Dtype data - category(region, station, street) and color type

    //mapping to Detail
}
