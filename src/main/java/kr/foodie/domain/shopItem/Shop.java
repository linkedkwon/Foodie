package kr.foodie.domain.shopItem;

import lombok.*;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.ClassicTokenizerFactory;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "RANKUP_SHOP_INTRODUCE_DATA")
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
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "NO")
    private Integer shopId;

    @Column(name = "SHOP_NAME")
    @Field
    @Analyzer(definition = "shopAnalyzer")
    private String shopName;

    @Column(name = "SHOP_ALIAS")
    @Field
    @Analyzer(definition = "shopAnalyzer")
    private String shopAlias;

    @Column(name = "SHOP_CHARGE")
    @Field
    @Analyzer(definition = "shopAnalyzer")
    private String shopCharge;

    @Column(name = "SHOP_PHONE")
    private String phone;

    @Column(name = "SHOP_PHONE_CHARGE")
    private String shopPhone;

    @Column(name = "SHOP_PAID")
    private String shopPaid;

    @Column(name = "SHOP_RANK")
    private String foodieLogRating;

    @Column(name = "SHOP_BG_IMAGE")
    private Integer shopType;

    @Column(name = "SHOP_1ST")
    private String bigCategory;

    @Column(name = "SHOP_2ND")
    private String middleCategory;

    @Column(name = "SHOP_3TH")
    private String smallCategory;

    @Column(name = "AREA_1ST")
    private Integer area1st;

    @Column(name = "AREA_2ND")
    private Integer area2st;

    @Column(name = "AREA_3TH")
    private Integer area3st;

    @Column(name = "AREA_ETC")
    private String areaEtc;

    @Column(name = "ADDRESS")
    @Field
    @Analyzer(definition = "shopAnalyzer")
    private String address;

    @Column(name = "ROAD_ADDRESS")
    @Field
    @Analyzer(definition = "shopAnalyzer")
    private String roadAddress;

    @Column(name = "SUBWAY_1ST")
    private String subwayTypeId = null;

    @Column(name = "SUBWAY_2ND")
    private String subway2st = null;

    @Column(name = "SUBWAY_3TH")
    private String subway3st = null;

    @Column(name = "EXIT_NUM")
    private Integer exitNum;

    @Column(name = "THEME")
    private String themeList;

    @Column(name = "SHOP_TOWN")
    private Integer villageTypeId;

    @Column(name = "EXPLANATION")
    @Field
    @Analyzer(definition = "shopAnalyzer")
    private String detailAddress;

    @Column(name = "MENU")
    @Field
    @Analyzer(definition = "shopAnalyzer")
    private String menu;

    @Column(name = "CONTENT")
    private String history;

    @Column(name = "PHOTO")
    private String menuImages;

    @Column(name = "COUPON")
    private String coupon;

    @Column(name = "COUPON_SDATE")
    private Date couponSdate;

    @Column(name = "COUPON_EDATE")
    private Date couponEdate;

    @Column(name = "MODIFY_DATE")
    private LocalDateTime updatedAt;

    @Column(name = "WRITE_DATE", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "ETC_2")
    private String operationTime;

    @Column(name = "ETC_3")
    private String holiday;

    @Column(name = "ETC_5")
    private String isReservation;

    @Column(name = "ETC_6")
    private String isParking;

//    ?
    @Column(name = "ETC_7")
    private String premiumRegisterDate;

    @Column(name = "ETC_9")
    private String homepage;

    @Column(name = "ETC_10")
    @Field
    @Analyzer(definition = "shopAnalyzer")
    private String recommandMenu;

    @Column(name = "ETC_11")
    private String budget;

    @Column(name = "IS_DELIVERY")
    private String isDelivery;

    //추가됨
    @Column(name = "SHOP_IMAGE")
    private String shopImage;

    //추가됨
    @Column(name = "BLOG_SHOP_ID")
    private String blogShopId;

    //추가됨
    @Column(name = "INSTA_KEYWORD")
    private String instaKeyword;

    //추가됨
    @Column(name = "TAG")
    private String tag;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Shop toEntity(ShopDTO shopDto, Integer shopId) {
        return Shop.builder()
                .shopId(shopId)
                .shopName(shopDto.getShopName())
                .shopAlias(shopDto.getShopAlias())
                .themeList(shopDto.getThemeList())
                .villageTypeId(shopDto.getVillageTypeId())
                .shopType(shopDto.getShopType())
                .shopPaid(shopDto.getShopPaid())
                .shopCharge(shopDto.getShopCharge())
                .tag(shopDto.getTag())
                .bigCategory(shopDto.getBigCategory())
                .middleCategory(shopDto.getMiddleCategory())
                .smallCategory(shopDto.getSmallCategory())
                .address(shopDto.getAddress())
                .roadAddress(shopDto.getRoadAddress())
                .area1st(shopDto.getArea1st())
                .area2st(shopDto.getArea2st())
                .area3st(shopDto.getArea3st())
                .subwayTypeId(shopDto.getSubwayTypeId())
                .subway2st(shopDto.getSubway2st())
                .subway3st(shopDto.getSubway3st())
                .phone(shopDto.getPhone())
//                .shopPhone(shopDto.getShopPhone())
                .detailAddress(shopDto.getDetailAddress())
                .operationTime(shopDto.getOperationTime())
                .holiday(shopDto.getHoliday())
                .recommandMenu(shopDto.getRecommandMenu())
                .isReservation(shopDto.getIsReservation())
                .isParking(shopDto.getIsParking())
                .foodieLogRating(shopDto.getFoodieLogRating())
//                .tasteRating(shopDto.getTasteRating())
                .budget(shopDto.getBudget())
                .isDelivery(shopDto.getIsDelivery())
                .homepage(shopDto.getHomepage())
                .history(shopDto.getHistory())
                .menuImages(shopDto.getMenuImages())
                .menu(shopDto.getMenu())
                .shopImage(shopDto.getShopImage())
                .blogShopId(shopDto.getBlogShopId())
                .instaKeyword(shopDto.getInstaKeyword())
                .exitNum(shopDto.getExitNum())
                .build()
                ;
    }

    public static Shop emptyShop() {
        return new Shop();
    }
}
