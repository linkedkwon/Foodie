package kr.foodie.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
  private int userId;
  private String name;
  private String email;
  private String phone;
  private int point;
}
