package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.service.PaginationService;
import kr.foodie.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/user/review")
public class ReviewController {

    private static final String url = "/user/review/";
    private static final int interval = 6;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PaginationService paginationService;

    @ResponseBody
    @PostMapping("/item")
    public String addReview(int shopId, String starRating, String content,
                            @AuthenticationPrincipal AuthUserDetails userDetails){
        return reviewService.addItem(userDetails.getUser().getId(), shopId, starRating, content);
    }

    @GetMapping({"","/{path}"})
    public String renderUserReview(@PathVariable Optional<String> path, Model model,
                                   @AuthenticationPrincipal AuthUserDetails userDetails){

        int idx = Integer.parseInt(path.orElseGet(()->{return "0";}));
        int userId = userDetails.getUser().getId();
        int size = reviewService.getItemSize(userId);
        String username = userDetails.getUser().getName();

        if(size == 0)
            return "mypage_tab3_nodata";

        model.addAttribute("size", size);
        model.addAttribute("reviews",reviewService.getItems(userId, idx, username));
        model.addAttribute("paginations", paginationService.getPagination(size, idx, interval, url));
        model.addAttribute("btnUrls", paginationService.getPaginationBtn(size, idx, interval, url));

        return "mypage_tab3";
    }
}
