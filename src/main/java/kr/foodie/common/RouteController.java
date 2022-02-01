package kr.foodie.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Base Rendering needed model
 */
@Controller
public class RouteController {

    @GetMapping("/gps")
    public String renderGps(){
        return "gps";
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
        return "detail-mustard_bak";
    }

    @GetMapping("/detail/red")
    public String renderDetailRed(){
        return "detail-red";
    }

    @GetMapping("/admin/index")
    public String renderAdminIndex(){
        return "admin-index";
    }

}
