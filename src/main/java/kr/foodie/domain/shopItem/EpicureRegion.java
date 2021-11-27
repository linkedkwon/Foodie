package kr.foodie.domain.shopItem;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Table(name = "EPICURE_REGION")
@Data
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PACKAGE)
@DynamicUpdate
public class EpicureRegion {

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

    public static EpicureRegion from(RegionCreateDTO dto) {
        return EpicureRegion.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .parentNo(dto.getParentNo())
                .listName(dto.getListName())
                .seq(dto.getSeq())
                .visiable(dto.getVisiable())
                .build();
    }
}
