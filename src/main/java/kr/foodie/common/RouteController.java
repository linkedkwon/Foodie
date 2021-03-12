package kr.foodie.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Base Rendering needed model
 */
@Controller
public class RouteController {

    @GetMapping({"", "/"})
    public String renderMain(){ return "main"; }

    @GetMapping("/gps")
    public String renderGps(){
        return "gps";
    }

    @GetMapping("/login")
    public String renderSingIn(){
        return "login";
    }

    @GetMapping("/register")
    public String renderSignUp(){
        return "signup";
    }

    @GetMapping("/mypage")
    public String renderMypage(){
        return "mypage_tab1";
    }

    @GetMapping("/submain/green")
    public String renderSubmainGreen(){
        return "submain-green";
    }

    @GetMapping("/submain/red")
    public String renderSubmainRed(){
        return "submain-red";
    }

    @GetMapping("/detail/green")
    public String renderDetailGreen(){
        return "detail-green";
    }

    @GetMapping("/detail/mustard")
    public String renderDetailMustard(){
        return "detail-mustard";
    }

    @GetMapping("/detail/red")
    public String renderDetailRed(){
        return "detail-red";
    }
}
