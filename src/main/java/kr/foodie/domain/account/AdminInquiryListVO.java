package kr.foodie.domain.account;

import lombok.Data;

@Data
public class AdminInquiryListVO {

    private int page;
    private String option;
    private String keyword;
    private String replied;

}
