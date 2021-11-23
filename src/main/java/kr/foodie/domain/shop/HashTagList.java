package kr.foodie.domain.shop;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "HASH_TAG_LIST")
@Data
public class HashTagList {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = IDENTITY)
    private Integer Id;

    @Column(name = "TAG_ID")
    private Integer tagId;

    @Column(name = "TAG_NAME")
    private String tagName;

    @Column(name = "TYPE")
    private String type;

}
