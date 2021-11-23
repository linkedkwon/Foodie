package kr.foodie.domain.account;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "INQUIRY")
@Data
@NoArgsConstructor
public class Inquiry {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "INQUIRY_id")
    private Long inquiryId;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "TITLE", length = 30)
    private String title;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "CONTENT", length = 400)
    private String content;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "CREATED_AT")
    private Date createdTime;

    @Column(name = "MODIFIED_AT")
    private Date modifiedTime;

    @Builder
    public Inquiry(Integer userId, String title, String userName, String content, String comment, Date createdTime, Date modifiedTime) {
        this.userId = userId;
        this.title = title;
        this.userName = userName;
        this.content = content;
        this.comment = comment;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }
}
