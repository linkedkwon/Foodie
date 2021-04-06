package kr.foodie.domain.account;

import javax.persistence.*;

@Entity
@Table(name = "FAVORITE_SHOP")
public class FavoriteShop {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private Integer id;

    @Column(name = "USER_ID", length = 11)
    private Integer userId;

    @Column(name = "SHOP_ID", length = 11)
    private Integer shopId;
}
