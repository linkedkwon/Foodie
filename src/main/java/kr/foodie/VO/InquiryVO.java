package kr.foodie.VO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class InquiryVO {
  private String content;
  private String title;
  private Integer userId;
  private String userName;
}
