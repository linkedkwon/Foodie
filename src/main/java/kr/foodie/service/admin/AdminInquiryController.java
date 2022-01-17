package kr.foodie.service.admin;

import kr.foodie.domain.account.AdminInquiryListVO;
import kr.foodie.domain.account.InquiryDTO;
import kr.foodie.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String renderInquiryList(@RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "option", required = false) String option,
                                    @RequestParam(value = "keyword", required = false) String keyword,
                                    @RequestParam(value = "replied", required = false) String givenReplyOption){
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

    @GetMapping("/inquiry/detail")
    public String renderInquiryDetail(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "option", required = false) String option, @RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "replied", required = false) String givenReplyOption, Model model){
        model.addAttribute("inquiry", adminInquiryService.getAdminInquiryById(id));
        return "admin-inquiry-detail";
    }

    @GetMapping("/inquiry/edit")
    public String renderInquiryEdit(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "option", required = false) String option, @RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "replied", required = false) String givenReplyOption, Model model) {
        model.addAttribute("inquiry", adminInquiryService.getAdminInquiryById(id));
        return "admin-inquiry-edit";
    }

    @ResponseBody
    @PostMapping("/inquiry/edit/info")
    public String updateInquiryInfo(@ModelAttribute InquiryDTO dto){
        adminInquiryService.updateUserInfo(dto);
        return "1";
    }

}
