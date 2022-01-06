package kr.foodie.domain.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USER_ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "TEL", length = 15)
    private String telNum;

    @Column(name = "PHONE")
    private String phoneNum;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EMAIL_INFO_TYPE")
    private String emailReceivedType;

    @Column(name = "SNS_INFO_TYPE")
    private String snsReceivedType;

    @Column(name = "POINT")
    private Integer point;

    //0 or 1
    @Column(name = "USER_TYPE")
    private String userType;

    //premium role type
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_TYPE")
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFIED_DATE")
    private Date lastModifiedDate;

    //oauth type
    @Column(name = "PROVIDER_TYPE")
    private String provider;

    @Column(name = "PROVIDER_ID")
    private String providerId;

    @Column(name = "VISITED_CNT")
    private Integer visitedCnt;

    @Column(name = "MEMO", length = 400)
    private String memo;

    //for oauth
    @Builder
    public User(String name, String email, String password, String address,
                String phoneNum, String emailReceivedType, String snsReceivedType,
                int point, String userType,
                RoleType role, Date createdDate, Date lastModifiedDate ,
                String provider, String providerId, int visitedCnt){
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNum = phoneNum;
        this.emailReceivedType = emailReceivedType;
        this.snsReceivedType = snsReceivedType;
        this.point = point;
        this.userType = userType;
        this.roleType = role;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.provider = provider;
        this.providerId = providerId;
        this.visitedCnt = visitedCnt;
    }
}
