package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.service.FavoriteShopService;
import kr.foodie.service.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@Controller
@RequestMapping("/user/favorite")
public class FavoriteShopController {

    private static final String url = "favorite";
    private static final int interval = 5;

    @Autowired
    private FavoriteShopService favoriteShopService;

    @Autowired
    private PaginationService paginationService;

    @ResponseBody
    @GetMapping("/shop/{shopId}")
    public String addFavoriteShop(@PathVariable String shopId,
                                  @AuthenticationPrincipal AuthUserDetails obj){
        return favoriteShopService.addItem(obj.getUser().getId(), Integer.parseInt(shopId));
    }

    @GetMapping({"","/{path}"})
    public String renderUserFavoriteShop(@PathVariable Optional<String> path, Model model,
                                         @AuthenticationPrincipal AuthUserDetails obj){

        int idx = Integer.parseInt(path.orElseGet(()->{return "0";}));
        int userId = obj.getUser().getId();
        int size = favoriteShopService.getItemSize(userId);
        if(size == 0)
            return "mypage_tab2_nodata";

        model.addAttribute("size", size);
        model.addAttribute("favoriteShops", favoriteShopService.getFavoriteShops(userId, idx));
        model.addAttribute("paginations", paginationService.getPagination(size, idx, interval, url));
        model.addAttribute("btnUrls", paginationService.getPaginationBtn(size, idx, interval, url));

        return "mypage_tab2";
    }

    @ResponseBody
    @GetMapping("/delete/item/{shopId}")
    public String deleteItem(@PathVariable String shopId,
                             @AuthenticationPrincipal AuthUserDetails obj){
        return favoriteShopService.deleteItem(obj.getUser().getId(), Integer.parseInt(shopId));
    }

    @ResponseBody
    @GetMapping("/delete/item/all")
    public String deleteAllItem(@AuthenticationPrincipal AuthUserDetails obj){
        return favoriteShopService.deleteAllItem(obj.getUser().getId());
    }
}
