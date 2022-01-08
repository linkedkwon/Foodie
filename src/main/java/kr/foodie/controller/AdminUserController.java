package kr.foodie.controller;

import kr.foodie.domain.user.AdminUserListVO;
import kr.foodie.service.MemberService;
import kr.foodie.service.admin.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/admin")
public class AdminUserController {

    private final AdminUserService adminUserService;
    private final MemberService memberService;

    /**
     * 1. 회원 관리
     */
    @GetMapping("/user/0/all")
    public String renderGeneralUserList(@RequestParam(value = "page", required = false) Integer page,
                                        @RequestParam(value = "userType", required = false) String userType, @RequestParam(value = "option", required = false) String option,
                                        @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "renderOption", required = false) String renderOption){
        return "admin-user-list";
    }

    @GetMapping("/user/1/all")
    public String renderRestaurantUserList(){
        return "admin-restaurant-list";
    }

    @ResponseBody
    @PostMapping("/user/list")
    public Map<String, Object> getUserInfo(@ModelAttribute AdminUserListVO vo){
        Map<String, Object> data = new HashMap<>();
        data.put("currentPage", vo.getPage() % 10 == 0 ? 9 : vo.getPage() % 10 - 1);
        data.put("item", adminUserService.getAdminUserList(vo));
        int size = adminUserService.getAllUserCount(vo);
        data.put("size", size);
        data.put("pages", adminUserService.getPages(vo, size));
        data.put("btns", adminUserService.getBtnPages(vo, size));

        return data;
    }

    @ResponseBody
    @PostMapping("/delete/user")
    public String deleteCheckedList(@RequestParam(value ="list[]") List<Integer> list){
        return adminUserService.deleteUserById(list);
    }

    @ResponseBody
    @PostMapping("/user/edit/memo")
    public String editMemo(@RequestParam(value = "id") Integer userId,
                           @RequestParam(value = "content") String content){
        return adminUserService.updateUserMemo(userId, content);
    }

    @ResponseBody
    @GetMapping("/user/memo")
    public String getMemo(@RequestParam(value = "id") Integer userId){
        return adminUserService.getMemo(userId);
    }

    /**
     * 2. 회원 상세(이메일 눌렀을 때)
     */
    @GetMapping("/user/detail")
    public String renderUserDetail(@RequestParam(value = "id") Integer userId, @RequestParam(value = "page") Integer page,
                                   @RequestParam(value = "userType") String userType, @RequestParam(value = "option") String option,
                                   @RequestParam(value = "keyword") String keyword, @RequestParam(value = "renderOption") String renderOption,
                                   Model model){
        model.addAttribute("user", memberService.findUserById(userId));
        return "admin-user-detail";
    }

    /**
     * 3. 회원 수정
     */
    @GetMapping("/user/edit")
    public String renderUserEdit(@RequestParam(value = "id") Integer userId, @RequestParam(value = "page") Integer page,
                                 @RequestParam(value = "userType") String userType, @RequestParam(value = "option") String option,
                                 @RequestParam(value = "keyword") String keyword, @RequestParam(value = "renderOption") String renderOption,
                                 Model model){
        model.addAttribute("user", memberService.findUserById(userId));
        return "admin-user-edit";
    }
}