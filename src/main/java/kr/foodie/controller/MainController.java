package kr.foodie.controller;

import kr.foodie.domain.shopItem.Shop;
import kr.foodie.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class MainController {

    private final ShopService shopService;

    @GetMapping({"","/"})
    public String renderMain(Model model){
//        model.addAttribute("redCommentList", shopService.getShopInfoByType(18));
//        model.addAttribute("greenCommentList", shopService.getShopInfoByType(19));
        return "main";
    }

    @ResponseBody
    @GetMapping(value ="/main/region/{regionType}")
    public List<Shop> getMainShopList(@PathVariable Integer regionType){
        return shopService.getShopInfoByType(regionType);
    }
}
