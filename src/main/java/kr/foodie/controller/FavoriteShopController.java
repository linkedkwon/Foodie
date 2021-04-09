package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.service.FavoriteShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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
        return favoriteShopService.addFavoriteShop(obj.getUser().getId(), Integer.parseInt(shopId));
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
}
