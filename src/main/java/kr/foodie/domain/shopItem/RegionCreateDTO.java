package kr.foodie.domain.shopItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionCreateDTO {

    private Integer id;
    private String code;
    private Integer parentNo;
    private String listName;
    private Integer seq;
    private String visiable;

}
