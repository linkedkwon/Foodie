package kr.foodie.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewVO {
  private String content;
  private String starRating;
  private String bestComment;
  private int userId;
}
