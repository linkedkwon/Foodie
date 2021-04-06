package kr.foodie.domain.account;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "REVIEW")
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //refer to each domain
    @Column(name = "SHOP_ID")
    private Integer shopId;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "STAR")
    private Integer star;

    @Column(name = "CONTENT")
    private Integer content;

    @Column(name = "CREATED_AT")
    private Timestamp createdTime;

    @Column(name = "DELETED_AT")
    private Integer deletedTime;

}
