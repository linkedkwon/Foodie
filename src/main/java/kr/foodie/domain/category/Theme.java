package kr.foodie.domain.category;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "THEME")
@Data
public class Theme {

    @Id
    @Column(name = "THEME_ID")
    private Integer themeId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    private Integer type;
}
