package kr.foodie.controller;

import kr.foodie.domain.user.AdminUserListVO;
import kr.foodie.service.admin.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/admin")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping("/user/0/all")
    public String renderGeneralUserList(){
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

}