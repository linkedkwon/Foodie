package kr.foodie.domain.shop;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "MAIN_BOARD")
@Data
public class MainBoard {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "SHOP_ID")
    private Integer shopId;

    @Column(name = "TYPE")
    private Integer type;
}
