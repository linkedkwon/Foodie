package kr.foodie.common;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private String errorCode;

    public CommonException(String errorCode) {
        this.errorCode = errorCode;
    }
}
