package kr.foodie.controller;

import kr.foodie.domain.category.FoodCategory;
import kr.foodie.domain.category.FoodCategory;
import kr.foodie.domain.shop.HashTag;
import kr.foodie.domain.shop.Region;
import kr.foodie.domain.shop.Shop;
import kr.foodie.service.*;
import kr.foodie.service.admin.FoodCategoryAdminService;
import kr.foodie.service.admin.RegionAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    private static final int shopInterval = 14;
    private static final int reviewInterval = 6;

    private final ShopService shopService;
    private final TagService tagService;
    private final FoodCategoryAdminService foodCategoryAdminService;
    private final RegionAdminService regionAdminService;
    private final ReviewService reviewService;
    private final PaginationService paginationService;

    public AdminController(ShopService shopService, TagService tagService,
                           RegionAdminService regionAdminService, FoodCategoryAdminService foodCategoryAdminService,
                           ReviewService reviewService, PaginationService paginationService) {
        this.shopService = shopService;
        this.tagService = tagService;
//        this.regionService = regionService;
        this.regionAdminService = regionAdminService;
        this.reviewService = reviewService;
        this.foodCategoryAdminService = foodCategoryAdminService;
        this.paginationService = paginationService;
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

    @RequestMapping(value ={"/shop/{shopType}/{shopId}","/{shopId}/{path}"}, method= RequestMethod.GET)
    public ModelAndView getShopDetail(@PathVariable String shopType, @PathVariable Integer shopId, @PathVariable Optional<Integer> path){

        ModelAndView mav = new ModelAndView();
        if(shopType.equals("red")){
            shopType = "0";
        }else{
            shopType = "1";
        }
        List<Shop> commentList;
        List<HashTag> hashTags;
        commentList = shopService.getShopDetail(shopId);
        hashTags = tagService.getHashTags(shopId);
        
        int idx = path.orElseGet(()->{return 0;});
        int size = reviewService.getItemSizeByShopId(shopId);
        String url = "/shop/"+shopId+"/";

        mav.addObject("reviews", reviewService.getItemsByShopId(shopId, idx));
        mav.addObject("paginations", paginationService.getPagination(size, idx, reviewInterval, url));
        mav.addObject("btnUrls", paginationService.getPaginationBtn(size, idx, reviewInterval, url));
        mav.addObject("payload", commentList);
        mav.addObject("hashTags",hashTags);
        mav.setViewName("admin-shop-detail");
        return mav;
    }


    @ResponseBody
    @RequestMapping(value ="/{type}/{id}", method= RequestMethod.GET)
    public List<FoodCategory> getCategorySelectInfos(Model model, @PathVariable String type, @PathVariable Integer id){
        List<FoodCategory> categoryInfoList = null;
        if(type.equals("mfood")){
            categoryInfoList = foodCategoryAdminService.getAdminRegionMCategory(id);
        }else if (type.equals("sfood")){
            categoryInfoList = foodCategoryAdminService.getAdminRegionSCategory(id);
        }
        return categoryInfoList;
    }
}
