package kr.foodie.domain.shop;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "SHOP")
@Data
public class Shop {

    @Id
    @Column(name = "SHOP_ID")
    private Integer shopId;

//    @Column(name = "REGION_TYPE_ID")
//    private String regionTypeId;

    @Column(name = "REGION_ID")
    private Integer regionId;

    @Column(name = "THEME_LIST")
    private String themeList;

    @Column(name = "SUBWAY_TYPE_ID")
    private String subwayTypeId;

    @Column(name = "VILLAGE_TYPE_ID")
    private String villageTypeId;

    @Column(name = "SHOP_TYPE")
    private String shopType;

    @Column(name = "Background")
    private Integer background;

    @Column(name = "order")
    private Integer order;

    @Column(name = "SHOP_NAME")
    private String shopName;

    @Column(name = "TAG")
    private String tag;

    @Column(name = "BIG_CATEGORY")
    private Integer bigCategory;

    @Column(name = "MIDDLE_CATEGORY")
    private Integer middleCategory;

    @Column(name = "SMALL_CATEGORY")
    private Integer smallCategory;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "DETAIL_ADDRESS")
    private String detailAddress;

    @Column(name = "OPERATION_TIME")
    private String operationTime;

    @Column(name = "HOLIDAY")
    private String holiday;

    @Column(name = "RECOMMAND_MENU")
    private String recommandMenu;

    @Column(name = "IS_RESERVATION")
    private String isReservation;

    @Column(name = "IS_PARKING")
    private String isParking;

    @Column(name = "FOODIELOG_RATING")
    private String foodieLogRating;

    @Column(name = "TASTE_RATING")
    private String tasteRating;

    @Column(name = "BUDGET")
    private String budget;

    @Column(name = "IS_DELIVERY")
    private String isDelivery;

    @Column(name = "HOMEPAGE")
    private String homepage;

    @Column(name = "HISTORY")
    private String history;

    @Column(name = "MENU_IMAGES")
    private String menuImages;

    @Column(name = "MENU")
    private String menu;

    @Column(name = "SHOP_IMAGE")
    private String shopImage;

    @Column(name = "BLOG_SHOP_ID")
    private String blogShopId;

    @Column(name = "INSTA_KEYWORD")
    private String instaKeyword;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @Column(name = "CREATED_AT")
    private Date createdAt;
}
