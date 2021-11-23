package kr.foodie.domain.category;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "FOOD_CATEGORY")
@Data
public class FoodCategory {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "BCODE")
    private Integer bCode;

    @Column(name = "MCODE")
    private Integer mCode;

    @Column(name = "SCODE")
    private Integer sCode;

    @Column(name = "LEVEL")
    private Integer level;

    @Column(name = "SEQ")
    private Integer seq;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Column(name = "TYPE")
    private String type;

}
