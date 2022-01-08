package kr.foodie.domain.user;

import lombok.Data;

@Data
public class AdminUserListVO {

    private int page;
    private String userType;
    private String option;
    private String keyword;
    private String renderOption;
}
