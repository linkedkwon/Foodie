package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.service.FavoriteShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@Controller
@RequestMapping("/user")
public class FavoriteShopController {

    @Autowired
    private FavoriteShopService favoriteShopService;


    @ResponseBody
    @GetMapping("/favorite/shop/{shopId}")
    public String addFavoriteShop(@PathVariable String shopId,
                                  @AuthenticationPrincipal AuthUserDetails obj){
        return favoriteShopService.addItem(obj.getUser().getId(), Integer.parseInt(shopId));
    }

    @GetMapping({"/favorite","/favorite/{path}"})
    public String renderUserFavoriteShop(@PathVariable Optional<String> path, Model model,
                                         @AuthenticationPrincipal AuthUserDetails obj){

        int idx = Integer.parseInt(path.orElseGet(()->{return "0";}));
        int userId = obj.getUser().getId();
        int size = favoriteShopService.getPageSize(userId);
        if(size == 0)
            return "mypage_tab2_nodata";

        model.addAttribute("size", size);
        model.addAttribute("favoriteShops", favoriteShopService.getFavoriteShops(userId, idx));
        model.addAttribute("paginations", favoriteShopService.getPagination(size, idx));
        model.addAttribute("btnUrls", favoriteShopService.getPaginationBtn(size, idx));

        return "mypage_tab2";
    }

    @ResponseBody
    @GetMapping("/favorite/delete/item/{shopId}")
    public String deleteItem(@PathVariable String shopId,
                             @AuthenticationPrincipal AuthUserDetails obj){
        return favoriteShopService.deleteItem(obj.getUser().getId(), Integer.parseInt(shopId));
    }

    @ResponseBody
    @GetMapping("/favorite/delete/item/all")
    public String deleteAllItem(@AuthenticationPrincipal AuthUserDetails obj){
        return favoriteShopService.deleteAllItem(obj.getUser().getId());
    }
}
