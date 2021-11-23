package kr.foodie.domain.account;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@Table(name = "FAVORITE_SHOP")
public class FavoriteShop {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(length = 11)
    private Integer id;

    @Column(name = "USER_ID", length = 11)
    private Integer userId;

    @Column(name = "SHOP_ID", length = 11)
    private Integer shopId;

    @Column(name = "CREATED_AT")
    private Date createdTime;

    @Column(name = "DELETED_AT")
    private Date deletedTime;

    @Builder
    public FavoriteShop(int userId, int shopId){
        this.userId = userId;
        this.shopId = shopId;
    }
}
