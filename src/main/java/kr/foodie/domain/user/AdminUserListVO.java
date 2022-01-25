package kr.foodie.domain.user;

import lombok.Builder;
import lombok.Data;

@Data
public class AdminUserListVO {

    private int page;
    private String userType;
    private String option;
    private String keyword;
    private String renderOption;

    @Builder
    public AdminUserListVO(int page, String userType, String option,
                           String keyword, String renderOption){
        this.page = page;
        this.userType = userType;
        this.option = option;
        this.keyword = keyword;
        this.renderOption = renderOption;
    }
}
