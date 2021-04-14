package kr.foodie.domain.account;

import lombok.*;

@Data
@NoArgsConstructor
public class ReviewDTO {

    private int shopId;
    private String shopName;
    private String userName;
    private String starRating;
    private String content;
    private String url;

    public ReviewDTO(int shopId, String shopName, String content, String starRating) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.content = content;
        this.starRating = starRating;
    }

    public ReviewDTO(String userName, String starRating, String content){
        this.userName = userName;
        this.content = content;
        this.starRating = starRating;
    }
}
