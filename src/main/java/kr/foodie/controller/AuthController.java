package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.domain.member.Member;
import kr.foodie.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/join")
    public String register(Model model){
        model.addAttribute("member", new Member());
        return "signup";
    }

    @ResponseBody
    @GetMapping(value = "/validate/{email}")
    public String validate(@PathVariable String email){ return memberService.validate(email); }


    @PostMapping(value = "/register")
    public String register(Member member){ return memberService.save(member); }


    @GetMapping("/login/**")
    public String preventLogin(@AuthenticationPrincipal AuthUserDetails userDetails){
        if(userDetails == null)
            return "/login";
        return "redirect:/";
    }
}
