package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.service.InquiryService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping("/new")
    public String renderInquiryForm(){
        return "inquiry-form";
    }

    @PostMapping("/create")
    public String createInquiry(String title, String content,
                                @AuthenticationPrincipal AuthUserDetails userDetails){
        inquiryService.createInquiry(userDetails.getUser().getId(),
                                    userDetails.getUser().getName(),
                                    title, content);
        return "redirect:/";
    }
}