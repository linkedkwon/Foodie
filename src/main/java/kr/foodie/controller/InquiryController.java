package kr.foodie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

    @GetMapping("/list")
    public String renderInquiryList(){
        return "inquiry";
    }

    @GetMapping("/form")
    public String renderInquiryForm(){
        return "inquiry-form";
    }

    @GetMapping("/detail")
    public String renderInquiryContents(){
        return "inquiry-detail";
    }
}
