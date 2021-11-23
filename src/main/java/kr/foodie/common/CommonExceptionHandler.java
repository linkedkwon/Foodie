package kr.foodie.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public String commonException(CommonException e) {
        return e.getErrorCode();
    }
}
