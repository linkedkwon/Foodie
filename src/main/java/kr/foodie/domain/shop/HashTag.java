package kr.foodie.domain.shop;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "HASH_TAG")
@Data
public class HashTag {

    @Id
    @Column(name = "ID")
    private Integer Id;

    @Column(name = "SHOP_ID")
    private Integer shopId;

    @Column(name = "TAG_NAME")
    private String tagName;

}
