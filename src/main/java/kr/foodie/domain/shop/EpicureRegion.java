package kr.foodie.domain.shop;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "EPICUREREGION")
@Data
public class EpicureRegion {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "PARENT_NO")
    private Integer parentNo;

    @Column(name = "LIST_NAME")
    private String listName;

    @Column(name = "SEQ")
    private Integer seq;

    @Column(name = "VISIABLE")
    private String visiable;
}
