package kr.foodie.domain.shop;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "MAIN_BOARD")
@Data
public class MainBoard {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "SHOP_ID")
    private Integer shopId;

    @Column(name = "TYPE")
    private Integer type;
}
