package kr.foodie.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultCode {

    SUCCESS("성공입니다."),
    FAIL("실패입니다.");

    private final String message;
}
