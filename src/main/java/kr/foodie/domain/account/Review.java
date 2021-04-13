package kr.foodie.domain.account;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "REVIEW")
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "SHOP_ID")
    private Integer shopId;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "STAR_RATING")
    private String starRating;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATED_AT")
    private Date createdTime;

    @Builder
    public Review(Integer shopId, Integer userId, String starRating,
                  String content, Date createdTime) {
        this.shopId = shopId;
        this.userId = userId;
        this.starRating = starRating;
        this.content = content;
        this.createdTime = createdTime;
    }
}
