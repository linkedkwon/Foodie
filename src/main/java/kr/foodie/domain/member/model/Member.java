package kr.foodie.domain.member.model;

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

    @Column(name = "NAME", nullable = false, length = 15)
    private String name;

    @Column(name = "EMAIL", nullable = false, length = 20)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 15)
    private String password;

    @Column(name = "TEL", length = 15)
    private String telNum;

    @Column(name = "PHONE", nullable = false, length = 15)
    private String phoneNum;

    @Column(name = "ADDRESS", nullable = false, length = 50)
    private String address;

    @Column(name = "EMAIL_INFO_TYPE", nullable = false, length = 1)
    private String emailReceivedType;

    @Column(name = "SNS_INFO_TYPE", nullable = false, length = 1)
    private String snsReceivedType;

    //0 or 1
    @Column(name = "MEMBER_TYPE", length = 1)
    private String memberType;

    //premium role type
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_TYPE")
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    //Identification with roleType object through interceptor mapped premium page
    /**
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPayedDate;
    */
}
