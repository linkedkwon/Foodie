package kr.foodie.domain.account;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Pagination {

    private int idx;
    private String path;
    private String classValue;

    @Builder
    public Pagination(int idx, String path, String classValue){
        this.idx = idx;
        this.path = path;
        this.classValue = classValue;
    }
}
