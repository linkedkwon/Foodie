package kr.foodie.controller;

import kr.foodie.domain.shop.Shop;
import kr.foodie.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path = "/shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @ResponseBody
    @GetMapping(value ="region/{regionTypeId}")
    public List<Shop> getCategory(Model model, @PathVariable String regionTypeId, HttpServletRequest request){
        List<Shop> commentList;
        commentList = shopService.getShopInfos(regionTypeId);
        return commentList;
    }

}
