package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.domain.user.User;
import kr.foodie.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/auth")
public class AuthController {

    private final UserService userService;

    //rendering
    @GetMapping("/join/user/1")
    public String renderSignUpMember(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/join/user/2")
    public String renderSignUpRestaurant(Model model){
        model.addAttribute("user", new User());
        return "signup_restaurants";
    }

    //check validation(=duplication)
    @ResponseBody
    @GetMapping(value = "/validate")
    public String validateEmail(@RequestParam String email){
        return userService.findEmailValidation(email);
    }

    @ResponseBody
    @GetMapping(value = "/validate")
    public String validatePhoneNum(@RequestParam String phoneNum){
        return userService.findPhoneNumValidation(phoneNum);
    }

    //register
    @PostMapping(value = "/register")
    public String register(User user){ return userService.save(user); }

    //prevent direct url after authenticated
    @GetMapping("/login/**")
    public String preventSignInAfterAuthenticated(@AuthenticationPrincipal AuthUserDetails userDetails){
        if(userDetails == null)
            return "/login";
        return "redirect:/";
    }

    @GetMapping("/join/**")
    public String preventSignUpAfterAuthenticated(@AuthenticationPrincipal AuthUserDetails userDetails){
        if(userDetails == null)
            return "/auth/join/user/1";
        return "redirect:/";
    }
}
