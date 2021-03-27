package kr.foodie.config.security.remember;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

//only for create
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "persistent_logins")
public class PersistentLogin {

    @Id
    @Column(length = 64)
    private String series;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(name = "last_used", nullable = false, length = 64)
    private Date lastUsed;

    @Builder
    public PersistentLogin(String series, String username, String token, Date lastUsed){
        this.series = series;
        this.username = username;
        this.token = token;
        this.lastUsed = lastUsed;
    }
}
