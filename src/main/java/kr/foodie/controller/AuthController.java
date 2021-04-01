package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.domain.member.Member;
import kr.foodie.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private MemberService memberService;


    @GetMapping("/join")
    public String renderSignUp(Model model){
        model.addAttribute("member", new Member());
        return "signup";
    }

    @ResponseBody
    @GetMapping(value = "/validate/{email}")
    public String validateEmail(@PathVariable String email){ return memberService.validateEmail(email); }

    @PostMapping(value = "/register")
    public String register(Member member){ return memberService.save(member); }

    @GetMapping("/login/**")
    public String preventSignInAfterAuthenticated(@AuthenticationPrincipal AuthUserDetails userDetails){
        if(userDetails == null)
            return "/login";
        return "redirect:/";
    }
}
