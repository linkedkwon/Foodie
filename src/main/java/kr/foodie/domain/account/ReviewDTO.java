package kr.foodie.domain.account;

import lombok.*;

@Data
@NoArgsConstructor
public class ReviewDTO {

    private int shopId;
    private int userId;
    private String shopName;
    private String userName;
    private String starRating;
    private int reviewId;
    private String content;
    private String url;

    public ReviewDTO(int shopId, String shopName, int reviewId, String content, String starRating) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.reviewId = reviewId;
        this.content = content;
        this.starRating = starRating;
    }

    public ReviewDTO(String userName, int userId, int reviewId, String starRating, String content){
        this.userName = userName;
        this.userId = userId;
        this.reviewId = reviewId;
        this.content = content;
        this.starRating = starRating;
    }
}
