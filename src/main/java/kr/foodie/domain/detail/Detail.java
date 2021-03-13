package kr.foodie.domain.detail;

import javax.persistence.*;

@Entity
@Table(name = "DETAIL")
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DETAIL_ID")
    private long id;

    @Column(name = "DETAIL_TITLE")
    private String title;

    @Column(name = "DETAIL_CATEGORY")
    private String category;

    @Column(name = "SCORE_BY_FOODIE")
    private String scoreByFoodie;

    @Column(name = "SCORE_BY_TASTE")
    private String scoreByTaste;

    @Column(name = "START_COUNT")
    private String starCount;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "TEL_NUM")
    private String telNum;

    @Column(name = "DIRECTION")
    private String direction;

    @Column(name = "OPENING")
    private String opening;

    @Column(name = "DAY_OFF")
    private String dayOff;

    @Column(name = "RECOMMENDED_MENU")
    private String recommendedMenu;

    @Column(name = "RESERVATION_TYPE")
    private String reservationType;

    @Column(name = "PARKING_TYPE")
    private String parkingType;

    @Column(name = "COST")
    private String cost;

    @Column(name = "PACK_OR_DELIVERY")
    private String packOrDelivery;

    @Column(name = "URL")
    private String url;

    @Column(name = "HISTORY")
    private String history;

    @Column(name = "MENU")
    private String menu;

    @Column(name = "SURTAX")
    private String surtax;

    //Dtype for color
    //img to be adding
    //mapping to 'Meta-detail'
    //mapping to 'Comment'
    //mapping to 'Favorites'

}

