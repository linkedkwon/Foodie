package kr.foodie.controller;

import kr.foodie.domain.account.AdminReviewListVO;
import kr.foodie.domain.account.ReviewDTO;
import kr.foodie.service.admin.AdminReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    @GetMapping("/review/all")
    public String renderAdminReviewList(){
        return "admin-comment-list";
    }

    @ResponseBody
    @PostMapping("/review/list")
    public Map<String, Object> getReviewInfo(@ModelAttribute AdminReviewListVO vo){
        Map<String, Object> data = new HashMap<>();
        data.put("currentPage", vo.getPage() % 10 == 0 ? 9 : vo.getPage() % 10 - 1);
        data.put("item", adminReviewService.getAdminReviewList(vo));
        int size = adminReviewService.getAllReviewCount(vo);
        data.put("size", size);
        data.put("pages", adminReviewService.getPages(vo, size));
        data.put("btns", adminReviewService.getBtnPages(vo, size));

        return data;
    }

    @ResponseBody
    @PostMapping("/delete/review")
    public String deleteCheckedList(@RequestParam(value ="list[]") List<Integer> list){
        return adminReviewService.deleteUserById(list);
    }

    @ResponseBody
    @PostMapping("/review/switch")
    public String switchReviewIsBest(@ModelAttribute ReviewDTO dto){
        return adminReviewService.switchReview(dto);
    }
}
