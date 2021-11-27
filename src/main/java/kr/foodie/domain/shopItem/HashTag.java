package kr.foodie.domain.shopItem;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "HASH_TAG")
@Data
public class HashTag {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = IDENTITY)
    private Integer Id;

    @Column(name = "SHOP_ID")
    private Integer shopId;

    @Column(name = "TAG_NAME")
    private String tagName;

}
