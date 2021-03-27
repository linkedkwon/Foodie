package kr.foodie.controller;


import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class AccountController {

    @Autowired
    MemberService memberService;

    @GetMapping("/info")
    public String renderUserInfo(Model model, @AuthenticationPrincipal AuthUserDetails obj){

        model.addAttribute(obj.getMember());
        return "mypage_tab1";
    }

    @GetMapping("/wish/item")
    public String renderUserWishItem(){
        return "mypage_tab2";
    }

    @GetMapping("/comment")
    public String renderUserComment(){
        return "mypage_tab3";
    }

    @GetMapping("pay")
    public String renderUserPayInfo(){
        return "mypage_tab4";
    }
}
