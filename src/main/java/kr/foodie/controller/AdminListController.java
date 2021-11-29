package kr.foodie.controller;

import kr.foodie.domain.shopItem.AdminShopListVO;
import kr.foodie.service.admin.AdminShopListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class AdminListController {

    private final AdminShopListService adminShopListService;

    @ResponseBody
    @PostMapping("/item/list")
    public Map<String, Object> getAdminShopList(@ModelAttribute AdminShopListVO vo) {

        Map<String, Object> data = new HashMap<>();
        data.put("currentPage", vo.getPage() % 10 - 1);
        data.put("count", adminShopListService.getCountList(vo));
        data.put("data", adminShopListService.getAdminShopList(vo));
        data.put("pages", adminShopListService.getPages(vo));
        data.put("btns", adminShopListService.getBtnPages(vo));

        return data;
    }
}