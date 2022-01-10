package kr.foodie.service.admin;

import kr.foodie.domain.account.AdminInquiryListVO;
import kr.foodie.domain.account.AdminReviewListVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminInquiryController {

    private final AdminInquiryService adminInquiryService;

    @GetMapping("/inquiry/all")
    public String renderInquiryList(){
        return "admin-inquiry-list";
    }

    @ResponseBody
    @PostMapping("/inquiry/list")
    public Map<String, Object> getReviewInfo(@ModelAttribute AdminInquiryListVO vo){

        Map<String, Object> data = new HashMap<>();
        data.put("currentPage", vo.getPage() % 10 == 0 ? 9 : vo.getPage() % 10 - 1);
        data.put("item", adminInquiryService.getAdminInquiryList(vo));
        int size = adminInquiryService.getAllInquiryCount(vo);
        data.put("size", size);
        data.put("pages", adminInquiryService.getPages(vo, size));
        data.put("btns", adminInquiryService.getBtnPages(vo, size));

        return data;
    }

    @ResponseBody
    @PostMapping("/delete/inquiry")
    public String deleteCheckedList(@RequestParam(value ="list[]") List<Integer> list){
        return adminInquiryService.deleteUserById(list);
    }

}
