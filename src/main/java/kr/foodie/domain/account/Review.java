package kr.foodie.domain.account;

import lombok.Builder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "REVIEW")
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "SHOP_ID")
    private Integer shopId;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "STAR_RATING")
    private String starRating;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATED_AT")
    private Date createdTime;

    @Builder
    public Review(Integer shopId, Integer userId, String userName,
                  String starRating, String content, Date createdTime) {
        this.shopId = shopId;
        this.userId = userId;
        this.userName = userName;
        this.starRating = starRating;
        this.content = content;
        this.createdTime = createdTime;
    }
}
