package kr.foodie.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "user")
public class UserEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userId;
  @Column
  private String name;
  @Column
  private String address;
  @Column
  private String email;
  @Column
  private String phone;
  @Column
  private Integer point;

  @Builder
  public UserEntity(Integer userId, String name, String address, String email, String phone, Integer point) {
    this.userId = userId;
    this.name = name;
    this.address = address;
    this.email = email;
    this.phone = phone;
    this.point = point;
  }
}
