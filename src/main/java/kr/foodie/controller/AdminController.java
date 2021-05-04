package kr.foodie.controller;

import kr.foodie.domain.category.FoodCategory;
import kr.foodie.domain.category.FoodCategory;
import kr.foodie.domain.shop.Region;
import kr.foodie.domain.shop.Shop;
import kr.foodie.service.RegionService;
import kr.foodie.service.ShopService;
import kr.foodie.service.TagService;
import kr.foodie.service.admin.FoodCategoryAdminService;
import kr.foodie.service.admin.RegionAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    private final ShopService shopService;
//    private final TagService tagService;
    private final FoodCategoryAdminService foodCategoryAdminService;
    private final RegionAdminService regionAdminService;

    public AdminController(ShopService shopService, TagService tagService, RegionAdminService regionAdminService, FoodCategoryAdminService foodCategoryAdminService) {
        this.shopService = shopService;
//        this.tagService = tagService;
//        this.regionService = regionService;
        this.regionAdminService = regionAdminService;
        this.foodCategoryAdminService = foodCategoryAdminService;
    }

    @GetMapping("/main")
    public String renderAdminShopRed(){
        return "admin-index";
    }

    @GetMapping("/shop/detail")
    public String renderAdminShopDetail(){
        return "admin-shop-detail";
    }

    @RequestMapping(value ="/shop/{shopType}", method= RequestMethod.GET)
    public ModelAndView getShopList(@PathVariable String shopType){
        ModelAndView mav = new ModelAndView();
        if(shopType.equals("red")){
            shopType = "0";
        }else{
            shopType = "1";
        }
        List<Shop> commentList;
        List<FoodCategory> categoryInfos;
//        List<Shop> sideCommentListWithOrder;
        List<String> regionInfos;
        regionInfos = regionAdminService.getRegionProvinceInfo();
        categoryInfos = foodCategoryAdminService.getAdminRegionBCategory();
        commentList = shopService.getAdminShopInfos(shopType);
//        commentListWithOrder = shopService.getShopInfosWithOrder(regionTypeId, shopType, 9);
//        sideCommentListWithOrder = shopService.getShopInfosWithSideOrder(regionTypeId, shopType, 8);
        mav.addObject("payload", commentList);
        mav.addObject("categoryInfos", categoryInfos);
        mav.addObject("regionInfos", regionInfos);
//        mav.addObject("regionInfo", regiaonInfos);
//        mav.addObject("priority", commentListWithOrder);
//        mav.addObject("sidePriority", sideCommentListWithOrder);
//        if(shopType.equals("0")){
        mav.setViewName("admin-shop-list");

        return mav;
    }
}
