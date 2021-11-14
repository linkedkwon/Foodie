package kr.foodie.domain.shop;

import lombok.Data;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.ClassicTokenizerFactory;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Data
public class ShopDTO {
    private Integer shopId;
    private Integer regionId;
    private String themeList;
    private Integer subwayTypeId;
    private Integer villageTypeId;
    private String shopType;
    private String background;
    private Date premiumRegisterDate;
    private String shopPaid;
    private String shopAlias;
    private String shopCharge;
    private String shopName;
    private String tag;
    private String bigCategory;
    private String middleCategory;
    private String smallCategory;
    private String address;
    private String roadAddress;
    private String phone;
    private String shopPhone;
    private String detailAddress;
    private String operationTime;
    private String holiday;
    private String recommandMenu;
    private String isReservation;
    private String isParking;
    private String foodieLogRating;
    private String tasteRating;
    private String budget;
    private String isDelivery;
    private String homepage;
    private String history;
    private String menuImages;
    private String menu;
    private String shopImage;
    private String blogShopId;
    private String instaKeyword;
    private Date updatedAt;
    private Date createdAt;
    private Integer exitNum;
}
