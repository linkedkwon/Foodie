package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.config.security.session.AuthenticationService;
import kr.foodie.domain.user.User;
import kr.foodie.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequiredArgsConstructor
@Controller
@RequestMapping("/user/info")
public class AccountController {

    private final MemberService memberService;
    private final AuthenticationService authenticationService;

    @GetMapping("")
    public String renderUserInfo(@AuthenticationPrincipal AuthUserDetails obj, Model model){
        model.addAttribute(memberService.findUserByEmail(obj.getUser().getEmail()));
        return "mypage_tab1";
    }

    @PostMapping("")
    public String editUserInfo(@AuthenticationPrincipal AuthUserDetails obj, User user){

        String phoneNum = Optional.ofNullable(user.getPhoneNum())
                                  .orElseGet(()->{return obj.getUser().getPhoneNum();});
        user.setPhoneNum(phoneNum);

        memberService.update(user);
        authenticationService.updateAuthentication(user.getEmail());
        return "redirect:/user/info";
    }

}