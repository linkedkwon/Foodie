package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.service.InquiryService;
import kr.foodie.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class InquiryController {

    private static final String url = "/user/inquiry/";
    private static final int interval = 5;

    private final InquiryService inquiryService;
    private final PaginationService paginationService;

    @GetMapping("/user/inquiry/new")
    public String renderInquiryForm(){
        return "inquiry-form";
    }

    @GetMapping({"/user/inquiry", "/user/inquiry/{path}"})
    public String renderInquiryList(@PathVariable Optional<String> path, Model model,
                                    @AuthenticationPrincipal AuthUserDetails userDetails){

        int userId = userDetails.getUser().getId();
        int size = inquiryService.getItemSizeByUserId(userId);
        int idx = Integer.parseInt(path.orElseGet(()->{return "0";}));
        if(size == 0)
            return "mypage_tab4_nodata";

        model.addAttribute("size", size);
        model.addAttribute("inquiries", inquiryService.getInquiryList(userId, idx));
        model.addAttribute("paginations", paginationService.getPagination(size, idx, interval, url));
        model.addAttribute("btnUrls", paginationService.getPaginationBtn(size, idx, interval, url));

        return "mypage_tab4";
    }

    @GetMapping("/user/inquiry/detail/{path}")
    public String renderInquiryDetail(@PathVariable String path, Model model){

        model.addAttribute("item", inquiryService.getInquiryByInquiryId(Long.parseLong(path)));
        return "inquiry-detail";
    }

    @PostMapping("/inquiry/create")
    public String createInquiry(String title, String content,
                                @AuthenticationPrincipal AuthUserDetails userDetails){
        inquiryService.createInquiry(userDetails.getUser().getId(),
                                    userDetails.getUser().getName(),
                                    title, content);
        return "redirect:/";
    }

    @ResponseBody
    @DeleteMapping("/user/inquiry/item/{idx}")
    public String deleteItem(@PathVariable String idx){
        return inquiryService.deleteItem(Long.parseLong(idx));
    }

    @ResponseBody
    @DeleteMapping("/user/inquiry/item")
    public String deleteItems(@AuthenticationPrincipal AuthUserDetails obj){
        return inquiryService.deleteAllItem(obj.getUser().getId());
    }
}