package kr.foodie.controller;

import kr.foodie.service.CacheService;
import kr.foodie.service.MailService;
import kr.foodie.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//related inquiry
@Controller
@RequestMapping("/help")
public class HelpController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MailService mailService;

    @Autowired
    private CacheService cacheService;


    @ResponseBody
    @PostMapping("/inquiry/id")
    public String validateIdInquiry(String name, String phoneNum){
        return memberService.inquiryId(name, phoneNum);
    }

    @ResponseBody
    @PostMapping("/inquiry/pw")
    public String validatePwInquiry(String email, String phoneNum) throws Exception {
        String findEmail = memberService.inquiryPw(email, phoneNum);
        if(findEmail.equalsIgnoreCase("1"))
            return "1";
        String code = cacheService.getRandomUserCode(findEmail);
        mailService.sendCode(findEmail, code);
        return "0";
    }

    @ResponseBody
    @PostMapping("/inquiry/code")
    public String validateUserCode(String email, String code) {
        String data = cacheService.checkCode(email, code);
        if(data.contains("1"))
            return data;
        String qs = cacheService.getRandomEmailCode();
        cacheService.setEmailQs(qs, email);
        return qs;
    }

    @GetMapping("/reset")
    public String renderResetPW(@RequestParam String email){
        return "reset-pw";
    }

    @PostMapping("/resetPW")
    public String updatePw(String qs, String password){
        String email = cacheService.getEmailByQs(qs);
        memberService.updatePassword(email, password);
        return "redirect:/auth/login";
    }
}
