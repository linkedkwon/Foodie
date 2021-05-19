package kr.foodie.domain.shop;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "HASH_TAG_LIST")
@Data
public class HashTagList {

    @Id
    @Column(name = "ID")
    private Integer Id;

    @Column(name = "TAG_ID")
    private Integer tagId;

    @Column(name = "TAG_NAME")
    private String tagName;

    @Column(name = "TYPE")
    private String type;

}
