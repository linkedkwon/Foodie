package kr.foodie.domain.account;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class InquiryDTO {

    private Long inquiryId;
    private String name;
    private String title;
    private String content;
    private String phoneNum;
    private String comment;
    private String givenReply;
    private String createdTime;

    public InquiryDTO(Long inquiryId, String name, String title,
                      String content, String phoneNum, String comment,
                      String givenReply, String createdTime) {
        this.inquiryId = inquiryId;
        this.name = name;
        this.title = title;
        this.content = content;
        this.phoneNum = phoneNum;
        this.comment = comment;
        this.givenReply = givenReply;
        this.createdTime = createdTime;
    }
}