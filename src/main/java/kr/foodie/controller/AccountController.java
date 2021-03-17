package kr.foodie.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {
    /**
     * member account modelDTO
     */
    @GetMapping("")
    public String renderAccount( ){
        return "mypage";
    }

}
