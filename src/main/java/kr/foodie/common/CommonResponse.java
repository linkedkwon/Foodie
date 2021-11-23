package kr.foodie.common;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

    @Builder.Default
    private ResultCode resultCode = ResultCode.SUCCESS;
    private String message;
    private T data;
}
