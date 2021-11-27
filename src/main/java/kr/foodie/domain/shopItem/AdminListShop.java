package kr.foodie.domain.shopItem;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_list_shop")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AdminListShop {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Integer no;

    @Column(name = "uid")
    private String uid;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "shop_alias")
    private String shopAlias;

    @Column(name = "shop_charge")
    private String shopCharge;

    @Column(name = "shop_phone")
    private String shopPhone;

    @Column(name = "shop_phone_charge")
    private String shopPhoneCharge;

    @Column(name = "shop_hmpage")
    private String shopHmpage;

    @Column(name = "shop_paid")
    private String shopPaid;

    @Column(name = "shop_new")
    private String shopNew;

    @Column(name = "shop_rank")
    private String shopRank;

    @Column(name = "shop_bg_image")
    private Integer shopBgImage;

    @Column(name = "shop_1st")
    private String shop1st;

    @Column(name = "shop_2nd")
    private String shop2nd;

    @Column(name = "shop_3th")
    private String shop3th;

    @Column(name = "area_1st")
    private Integer area1st;

    @Column(name = "area_2nd")
    private Integer area2nd;

    @Column(name = "area_3th")
    private Integer area3th;

    @Column(name = "area_etc")
    private String areaEtc;

    @Column(name = "address")
    private String address;

    @Column(name = "subway_1st")
    private Integer subway1st;

    @Column(name = "subway_2nd")
    private Integer subway2nd;

    @Column(name = "subway_3th")
    private Integer subway3th;

    @Column(name = "exit_num")
    private Integer exitNum;

    @Column(name = "map_infos")
    private String mapInfos;

    @Column(name = "theme")
    private String theme;

    @Column(name = "shop_town")
    private String shopTown;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "blog")
    private String blog;

    @Column(name = "menu")
    private String menu;

    @Column(name = "content")
    private String content;

    @Column(name = "photo")
    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon")
    private RankUpType couponFlag;

    @Column(name = "coupon_1st")
    private String coupon1st;

    @Column(name = "coupon_sdate")
    private LocalDateTime couponSDate;

    @Column(name = "coupon_edate")
    private LocalDateTime couponEDate;

    @Column(name = "coupon_gift")
    private String couponGift;

    @Column(name = "coupon_content")
    private String couponContent;

    @Column(name = "evaluation")
    private Integer evaluation;

    @Column(name = "evaluation_cnt")
    private Integer evaluationCnt;

    @Column(name = "hit")
    private Integer hit;

    @Column(name = "write_date")
    private LocalDateTime writedDate;

    @Column(name = "modify_date")
    private LocalDateTime modifiedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "open")
    private RankUpType open;

    @Enumerated(EnumType.STRING)
    @Column(name = "use_mh")
    private RankUpType useMh;

    @Enumerated(EnumType.STRING)
    @Column(name = "admin")
    private RankUpType admin;

    @Column(name = "etc_2")
    private String etc2;

    @Column(name = "etc_3")
    private String etc3;

    @Column(name = "etc_4")
    private String etc4;

    @Column(name = "etc_5")
    private String etc5;

    @Column(name = "etc_6")
    private String etc6;

    @Column(name = "etc_7")
    private String etc7;

    @Column(name = "etc_8")
    private String etc8;

    @Column(name = "etc_9")
    private String etc9;

    @Column(name = "etc_10")
    private String etc10;

    @Column(name = "etc_11")
    private String etc11;
}
