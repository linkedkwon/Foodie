package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.config.security.session.AuthenticationService;
import kr.foodie.domain.user.User;
import kr.foodie.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
@RequestMapping("/user/info")
public class AccountController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("")
    public String renderUserInfo(@AuthenticationPrincipal AuthUserDetails obj, Model model){
        model.addAttribute(obj.getUser());
        return "mypage_tab1";
    }

    @PostMapping("")
    public String editUserInfo(User user){
        userService.update(user);
        authenticationService.updateAuthentication(user.getEmail());
        return "redirect:/user/info";
    }
}