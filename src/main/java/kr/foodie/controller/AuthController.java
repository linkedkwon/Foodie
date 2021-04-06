package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.domain.user.User;
import kr.foodie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private UserService userService;


    @GetMapping("/join/user1")
    public String renderSignUpMember(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/join/user2")
    public String renderSignUpRestaurant(Model model){
        model.addAttribute("user", new User());
        return "signup_restaurants";
    }

    @ResponseBody
    @GetMapping(value = "/validate/{email}")
    public String validateEmail(@PathVariable String email){ return userService.validateEmail(email); }

    @PostMapping(value = "/register")
    public String register(User user){ return userService.save(user); }

    @GetMapping("/login/**")
    public String preventSignInAfterAuthenticated(@AuthenticationPrincipal AuthUserDetails userDetails){
        if(userDetails == null)
            return "/login";
        return "redirect:/";
    }
}
