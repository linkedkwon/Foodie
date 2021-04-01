package kr.foodie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"","/"})
    public String renderMain(Model model){
        model.addAttribute("hi", "hello");
        return "main";
    }
}
