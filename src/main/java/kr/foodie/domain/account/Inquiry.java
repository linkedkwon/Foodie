package kr.foodie.domain.account;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "INQUIRY")
@Data
@NoArgsConstructor
public class Inquiry {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INQUIRY_id")
    private Long inquiryId;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "CREATED_AT")
    private Date createdTime;

    @Column(name = "MODIFIED_AT")
    private Date modifiedTime;
}
