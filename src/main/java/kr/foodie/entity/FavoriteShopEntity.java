package kr.foodie.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "favorite_shop")
public class FavoriteShopEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column
  private Integer userId;
  @Column
  private Integer shopId;
  @Column
  private Date createdAt;

  @Builder
  public FavoriteShopEntity(Integer userId, Integer shopId, Date createdAt) {
    this.userId = userId;
    this.shopId = shopId;
    this.createdAt = createdAt;
  }
}