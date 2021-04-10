package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @ResponseBody
    @PostMapping("/item")
    public String addReview(int shopId, String starRating, String content,
                            @AuthenticationPrincipal AuthUserDetails userDetails){

        return reviewService.addReview(userDetails.getUser().getId(),
                                       userDetails.getUser().getName(),
                                       shopId, starRating, content);
    }

    @GetMapping({"/","/{path}"})
    public String renderUserComment(){
        return "mypage_tab3";
    }
}
