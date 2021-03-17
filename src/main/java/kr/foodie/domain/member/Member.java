package kr.foodie.domain.member;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "\"MEMBER\"")
@Data
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
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

    //0 or 1
    @Column(name = "MEMBER_TYPE")
    private String memberType;

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
    @Column(name = "PROVIDER")
    private String provider;

    @Column(name = "PROVIDER_ID")
    private String providerId;

}
