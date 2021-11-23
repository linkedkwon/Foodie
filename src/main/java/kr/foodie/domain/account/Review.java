package kr.foodie.domain.account;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@Table(name = "REVIEW")
public class Review {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "REVIEW_ID")
    private Integer reviewId;

    @Column(name = "SHOP_ID")
    private Integer shopId;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "STAR_RATING")
    private String starRating;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "BEST_COMMENT")
    private String bestComment;

    @Column(name = "CREATED_AT")
    private Date createdTime;

    @Builder
    public Review(Integer shopId, Integer userId, String starRating,
                  String content, String bestComment, Date createdTime) {
        this.shopId = shopId;
        this.userId = userId;
        this.starRating = starRating;
        this.content = content;
        this.bestComment = bestComment;
        this.createdTime = createdTime;
    }
}
