package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.service.FavoriteShopService;
import kr.foodie.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user/favorite")
public class FavoriteShopController {

    private static final String url = "/user/favorite/";
    private static final int interval = 5;

    private final FavoriteShopService favoriteShopService;
    private final PaginationService paginationService;

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
    @GetMapping("/item/{idx}")
    public String addItem(@PathVariable String idx, @AuthenticationPrincipal AuthUserDetails obj){
        return favoriteShopService.addItem(obj.getUser().getId(), Integer.parseInt(idx));
    }

    @ResponseBody
    @DeleteMapping("/item/{idx}")
    public String deleteItem(@PathVariable String idx,
                             @AuthenticationPrincipal AuthUserDetails obj){
        return favoriteShopService.deleteItem(obj.getUser().getId(), Integer.parseInt(idx));
    }

    @ResponseBody
    @DeleteMapping("/item")
    public String deleteItems(@AuthenticationPrincipal AuthUserDetails obj){
        return favoriteShopService.deleteAllItem(obj.getUser().getId());
    }
}
