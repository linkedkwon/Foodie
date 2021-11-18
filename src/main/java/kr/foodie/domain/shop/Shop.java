package kr.foodie.domain.shop;

import lombok.*;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.ClassicTokenizerFactory;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.search.annotations.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "SHOP")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Indexed
@AnalyzerDef(
        name = "shopAnalyzer",
        tokenizer = @TokenizerDef(factory = ClassicTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class)
        }
)
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHOP_ID")
    private Integer shopId;

    @Column(name = "REGION_ID")
    private Integer regionId;

    @Column(name = "THEME_LIST")
    private String themeList;

    @Column(name = "SUBWAY_TYPE_ID")
    private Integer subwayTypeId;

    @Column(name = "VILLAGE_TYPE_ID")
    private Integer villageTypeId;

    @Column(name = "SHOP_TYPE")
    private String shopType;

    @Column(name = "Background")
    private String background;

    @Column(name = "PremiumRegisterDate")
    private Date premiumRegisterDate;

    @Column(name = "SHOP_PAID")
    private String shopPaid;

    @Column(name = "SHOP_ALIAS")
    @Field
    @Analyzer(definition = "shopAnalyzer")
    private String shopAlias;

    @Column(name = "SHOP_CHARGE")
    @Field
    @Analyzer(definition = "shopAnalyzer")
    private String shopCharge;

    @Column(name = "SHOP_NAME")
    @Field
    @Analyzer(definition = "shopAnalyzer")
    private String shopName;

    @Column(name = "TAG")
    private String tag;

    @Column(name = "BIG_CATEGORY")
    private String bigCategory;

    @Column(name = "MIDDLE_CATEGORY")
    private String middleCategory;

    @Column(name = "SMALL_CATEGORY")
    private String smallCategory;

    @Column(name = "ADDRESS")
    @Field
    @Analyzer(definition = "shopAnalyzer")
    private String address;

    @Column(name = "ROAD_ADDRESS")
    @Field
    @Analyzer(definition = "shopAnalyzer")
    private String roadAddress;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "SHOP_PHONE")
    private String shopPhone;

    @Column(name = "DETAIL_ADDRESS")
    @Field
    @Analyzer(definition = "shopAnalyzer")
    private String detailAddress;

    @Column(name = "OPERATION_TIME")
    private String operationTime;

    @Column(name = "HOLIDAY")
    private String holiday;

    @Column(name = "RECOMMAND_MENU")
    @Field
    @Analyzer(definition = "shopAnalyzer")
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
    @Field
    @Analyzer(definition = "shopAnalyzer")
    private String menu;

    @Column(name = "SHOP_IMAGE")
    private String shopImage;

    @Column(name = "BLOG_SHOP_ID")
    private String blogShopId;

    @Column(name = "INSTA_KEYWORD")
    private String instaKeyword;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "EXIT_NUM")
    private Integer exitNum;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Shop toEntity(ShopDTO shopDto, Integer shopId) {
        String shopType;
        if(this.shopType.equals("red")){
            shopType = "0";
        }else{
            shopType = "1";
        }
        return Shop.builder()
                .shopId(shopId)
                .shopName(shopDto.getShopName())
                .shopAlias(shopDto.getShopAlias())
                .address(shopDto.getAddress())
                .blogShopId(shopDto.getBlogShopId())
                .shopType(shopType)

                .build()
                ;
    }

    public static Shop emptyShop() {
        return new Shop();
    }
}
