package kr.foodie.domain.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

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

    //for oauth
    @Builder
    public User(String name, String email, String address,
                String emailReceivedType, String snsReceivedType,
                int point, String userType,
                RoleType role, Date createdDate, Date lastModifiedDate ,
                String provider, String providerId){
        this.name = name;
        this.email = email;
        this.address = address;
        this.emailReceivedType = emailReceivedType;
        this.snsReceivedType = snsReceivedType;
        this.point = point;
        this.userType = userType;
        this.roleType = role;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.provider = provider;
        this.providerId = providerId;
    }
}
