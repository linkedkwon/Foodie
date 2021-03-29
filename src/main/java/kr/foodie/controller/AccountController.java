package kr.foodie.controller;


import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.config.security.session.AuthenticationService;
import kr.foodie.domain.member.Member;
import kr.foodie.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class AccountController {

    @Autowired
    MemberService memberService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/info")
    public String renderUserInfo(@AuthenticationPrincipal AuthUserDetails obj, Model model){
        model.addAttribute(obj.getMember());
        return "mypage_tab1";
    }

    @PostMapping("/info/edit")
    public String editUserInfo(Member member){
        memberService.update(member);
        authenticationService.updateAuthentication(member.getEmail());

        return "redirect:/user/info";
    }

    @GetMapping("/wishItem")
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
