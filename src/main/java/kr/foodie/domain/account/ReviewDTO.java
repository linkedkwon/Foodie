package kr.foodie.domain.account;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
public class ReviewDTO {

    private int shopId;
    private int userId;
    private String shopName;
    private String userName;
    private String title;
    private String starRating;
    private int reviewId;
    private String content;
    private String url;
    private String bestComment;
    private String createdTime;
    private int point;

    public ReviewDTO(String starRating){
        this.starRating = starRating;
    }

    public ReviewDTO(int shopId, String shopName, int reviewId, String content,
                     String starRating, String bestComment) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.reviewId = reviewId;
        this.content = content;
        this.starRating = starRating;
        this.bestComment = bestComment;

    }

    public ReviewDTO(String userName, int userId, int reviewId, String starRating,
                     String content, String bestComment){
        this.userName = userName;
        this.userId = userId;
        this.reviewId = reviewId;
        this.content = content;
        this.starRating = starRating;
        this.bestComment = bestComment;
    }

    public ReviewDTO(int shopId, String shopName, int userId, String userName, int reviewId,
                     String starRating, String content, String bestComment, String createdTime){
        this.shopId = shopId;
        this.shopName = shopName;
        this.userId = userId;
        this.userName = userName;
        this.reviewId = reviewId;
        this.content = content;
        this.starRating = starRating;
        this.bestComment = bestComment;
        this.createdTime = createdTime;
    }

    public ReviewDTO(int shopId, String shopName, int userId, String userName, int reviewId,
                     String starRating, String content, String bestComment, int point, String createdTime){
        this.shopId = shopId;
        this.shopName = shopName;
        this.userId = userId;
        this.userName = userName;
        this.reviewId = reviewId;
        this.content = content;
        this.starRating = starRating;
        this.bestComment = bestComment;
        this.createdTime = createdTime;
        this.point = point;
    }
}
