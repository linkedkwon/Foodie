package kr.foodie.config.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

//only for create
@Entity
@Getter
@Setter
@Table(name = "persistent_logins")
public class PersistentId {

    @Id
    @Column(length = 64)
    private String series;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(name = "last_used", nullable = false, length = 64)
    private Date lastUsed;
}
