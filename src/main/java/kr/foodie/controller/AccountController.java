package kr.foodie.controller;


import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.config.security.session.AuthenticationService;
import kr.foodie.domain.shop.Shop;
import kr.foodie.domain.user.User;
import kr.foodie.service.FavoriteShopService;
import kr.foodie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private FavoriteShopService favoriteShopService;

    @Autowired
    private AuthenticationService authenticationService;


    @GetMapping("/info")
    public String renderUserInfo(@AuthenticationPrincipal AuthUserDetails obj, Model model){
        model.addAttribute(obj.getUser());
        return "mypage_tab1";
    }

    @PostMapping("/info/edit")
    public String editUserInfo(User user){
        userService.update(user);
        authenticationService.updateAuthentication(user.getEmail());

        return "redirect:/user/info";
    }

    @GetMapping({"/favorite","/favorite/{path}"})
    public String renderUserFavoriteShop(@PathVariable Optional<String> path, Model model,
                                         @AuthenticationPrincipal AuthUserDetails obj){

        String idx = path.orElseGet(()->{return "0";});
        int userId = obj.getUser().getId();
        int size = favoriteShopService.getPageSize(userId);
        if(size == 0)
            return "mypage_tab2_nodata";

        List<Shop> list = favoriteShopService.getFavoriteShops(userId, Integer.parseInt(idx));
        model.addAttribute("size", size);
        model.addAttribute("favoriteShops", list);

        return "mypage_tab2";
    }

    @ResponseBody
    @GetMapping("/favorite/shop/{shopId}")
    public String addFavoriteShop(@PathVariable String shopId,
                                  @AuthenticationPrincipal AuthUserDetails obj){
        return favoriteShopService.addFavoriteShop(obj.getUser().getId(), Integer.parseInt(shopId));
    }

    @GetMapping("/comment")
    public String renderUserComment(){
        return "mypage_tab3";
    }

    @GetMapping("pay")
    public String renderUserPayInfo(){
        return "mypage_tab4";
    }
}
