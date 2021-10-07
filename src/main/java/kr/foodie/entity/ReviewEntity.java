package kr.foodie.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "review")
public class ReviewEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer reviewId;
  @Column
  private String content;
  @Column
  private Date createdAt;
  @Column
  private Integer shopId;
  @Column
  private String starRating;
  @Column
  private Integer userId;
  @Column
  private String bestComment;

  @Builder
  public ReviewEntity(String content, Date createdAt, Integer shopId, String starRating, Integer userId, String bestComment) {
    this.content = content;
    this.createdAt = createdAt;
    this.shopId = shopId;
    this.starRating = starRating;
    this.userId = userId;
    this.bestComment = bestComment;
  }
}
