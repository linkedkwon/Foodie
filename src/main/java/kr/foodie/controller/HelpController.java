package kr.foodie.controller;

import kr.foodie.config.cache.CacheService;
import kr.foodie.service.MailService;
import kr.foodie.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class HelpController {

    private final UserService userService;
    private final MailService mailService;
    private final CacheService cacheService;

    @ResponseBody
    @PostMapping("/inquiry/id")
    public String inquiryId(String name, String phoneNum){
        return userService.inquiryId(name, phoneNum);
    }

    @ResponseBody
    @PostMapping("/inquiry/pw")
    public String inquiryPw(String email, String phoneNum) throws Exception {
        if(userService.inquiryPw(email, phoneNum).equalsIgnoreCase("1"))
            return "1";
        mailService.sendCode(email, cacheService.saveAuthCode(email));

        return "0";
    }

    @ResponseBody
    @PostMapping("/inquiry/code")
    public String inquiryAuthCode(String email, String code) {
        if(cacheService.findByAuthenticationCode(email, code).equals("0"))
            return "1";
        String encryptedCode = cacheService.getRandomEncryptedCode();
        cacheService.saveEncryptedCodeWithEmail(encryptedCode, email);

        return encryptedCode;
    }

    @GetMapping("/help/reset")
    public String renderResetPw(@RequestParam String email){
        return "reset-pw";
    }

    /**
     * exceptional(not designed to rest)
     * only use for help controller with encrypted qs
     */
    @PutMapping("/user/pw")
    public String updatePwByEncryptedCode(String encryptedCode, String password){
        userService.updatePassword(cacheService.findByEncryptedCode(encryptedCode), password);
        return "redirect:/auth/login";
    }
}
