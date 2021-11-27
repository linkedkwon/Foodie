package kr.foodie.domain.shopItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "RANKUP_SHOP_TOWN")
@Data
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PACKAGE)
@DynamicUpdate
public class ShopTown {

    @Id
    @Column(name = "NO")
    @GeneratedValue(strategy = IDENTITY)
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

    public static ShopTown from(RegionCreateDTO dto) {
        return ShopTown.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .parentNo(dto.getParentNo())
                .listName(dto.getListName())
                .seq(dto.getSeq())
                .visiable(dto.getVisiable())
                .build();
    }
}
