package kr.foodie.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kr.foodie.domain.category.FoodCategory;
import kr.foodie.domain.category.Theme;
import kr.foodie.domain.shop.*;
import kr.foodie.service.*;
import kr.foodie.service.admin.FoodCategoryAdminService;
import kr.foodie.service.admin.RegionAdminService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.crypto.Data;
import java.util.*;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    private static final int shopInterval = 14;
    private static final int reviewInterval = 6;

    private final ShopService shopService;
    private final TagService tagService;
    private final TagListService tagListService;
    private final FoodCategoryAdminService foodCategoryAdminService;
    private final RegionAdminService regionAdminService;
    private final ReviewService reviewService;
    private final PaginationService paginationService;
    private final ThemeService themeService;
    private final RegionService regionService;

    public AdminController(ShopService shopService, TagService tagService,
                           RegionAdminService regionAdminService, FoodCategoryAdminService foodCategoryAdminService,
                           ReviewService reviewService, PaginationService paginationService, ThemeService themeService, RegionService regionService, TagListService tagListService) {
        this.shopService = shopService;
        this.tagService = tagService;
//        this.regionService = regionService;
        this.regionAdminService = regionAdminService;
        this.reviewService = reviewService;
        this.foodCategoryAdminService = foodCategoryAdminService;
        this.paginationService = paginationService;
        this.themeService = themeService;
        this.regionService = regionService;
        this.tagListService = tagListService;
    }

    @GetMapping("/main")
    public String renderAdminShopRed(){
        return "admin-index";
    }

    @GetMapping("/shop/detail")
    public String renderAdminShopDetail(){
        return "admin-shop-detail";
    }

    @RequestMapping(value = "/registerEnv/{shopType}", method = RequestMethod.GET)
    public ModelAndView getregisterEnv(@PathVariable String shopType){
        ModelAndView mav = new ModelAndView();
        if(shopType.equals("red")){
            shopType = "0";
        }else{
            shopType = "1";
        }
        List<FoodCategory> categoryInfos;
        List<FoodCategory> categoryMInfos;
        List<FoodCategory> categorySInfos;
        categoryInfos = foodCategoryAdminService.getAdminRegionBCategory();
        categoryMInfos = foodCategoryAdminService.getAdminRegionMCategory(100);
        categorySInfos = foodCategoryAdminService.getAdminRegionSCategory(1000);
        mav.addObject("categoryInfos", categoryInfos);
        mav.addObject("categoryMInfos", categoryMInfos);
        mav.addObject("categorySInfos", categorySInfos);
        mav.setViewName("admin-register-env");
        return mav;
    }

    @RequestMapping(value = "/recommand/main/region", method = RequestMethod.GET)
    public ModelAndView getRecommandMainRegion(){
        ModelAndView mav = new ModelAndView();
//        mav.addObject("payload", shopService.getShopInfoByType(18));
        mav.setViewName("admin-recommand-main-region");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value ={"/recommand/main/regionInfo/{shopType}"}, method= RequestMethod.GET)
    public Map<String, List> getRecommandMainRegionInfo(Model model, @PathVariable Integer shopType){
        List<Shop> commentList;
        commentList = shopService.getShopInfoByType(shopType);
        Map<String, List> members = new HashMap<>();
        members.put("data", commentList);
        return members;
    }

    @RequestMapping(value = "/registerEnvRegion", method = RequestMethod.GET)
    public ModelAndView getregisterEnvRegion(){
        ModelAndView mav = new ModelAndView();
        List<String> regionsInfos;
        regionsInfos = regionAdminService.getRegionProvinceInfo();
//        regions = regionAdminService.findRegionProvinceInfoAll();
//        categoryMInfos = foodCategoryAdminService.getAdminRegionMCategory(100);
//        categorySInfos = foodCategoryAdminService.getAdminRegionSCategory(1000);
        mav.addObject("regionInfos", regionsInfos);
//        mav.addObject("categoryMInfos", categoryMInfos);
//        mav.addObject("provinceInfos", regions);
        mav.setViewName("admin-register-env-region");
        return mav;
    }

    @RequestMapping(value = "/registerEnvFoodVillege", method = RequestMethod.GET)
    public ModelAndView getregisterEnvFoodVillege(){
        ModelAndView mav = new ModelAndView();
        List<Region> foodRegionInfos;
        foodRegionInfos = regionAdminService.getRegionFoodRegionInfo();
        mav.addObject("foodRegionInfos", foodRegionInfos);
        mav.setViewName("admin-register-env-foodvillege");
        return mav;
    }

    @RequestMapping(value = "/registerEnvSubway", method = RequestMethod.GET)
    public ModelAndView getregisterEnvSubway(){
        ModelAndView mav = new ModelAndView();
        List<String> categoryInfos;
        List<FoodCategory> categoryMInfos;
        List<FoodCategory> categorySInfos;
        categoryInfos = regionAdminService.getRegionProvinceInfoWithRegionType2();
        mav.addObject("categoryInfos", categoryInfos);
        mav.setViewName("admin-register-env-subway");
        return mav;
    }

    @RequestMapping(value = "/registerEnvHastag", method = RequestMethod.GET)
    public ModelAndView getregisterEnvHashTag(){
        ModelAndView mav = new ModelAndView();
        List<HashTagList> redHashTagListInfos;
        List<HashTagList> greenHashTagListInfos;
        redHashTagListInfos = tagListService.getAllHashTags("0");
        greenHashTagListInfos = tagListService.getAllHashTags("1");
        mav.setViewName("admin-register-env-hashtag-red");
        mav.addObject("redHashTagListInfos", redHashTagListInfos);
        mav.addObject("greenHashTagListInfos", greenHashTagListInfos);
        return mav;
    }

    @RequestMapping(value = "/registerEnvTheme", method = RequestMethod.GET)
    public ModelAndView getregisterEnvTheme(){
        ModelAndView mav = new ModelAndView();
        List<Theme> redthemeListInfos;
        List<Theme> greenthemeListInfos;
        redthemeListInfos = themeService.getThemeTags(0);
        greenthemeListInfos = themeService.getThemeTags(1);
        mav.setViewName("admin-register-env-theme-red");
        mav.addObject("redThemeListInfos", redthemeListInfos);
        mav.addObject("greenThemeListInfos", greenthemeListInfos);
        return mav;
    }

    @RequestMapping(value = "/registerRedShop", method = RequestMethod.GET)
    public ModelAndView getregisterRedShop(){
        ModelAndView mav = new ModelAndView();
        List<Theme> redthemeListInfos;
        List<Theme> greenthemeListInfos;
        redthemeListInfos = themeService.getThemeTags(0);
//        greenthemeListInfos = themeService.getThemeTags(1);
        mav.setViewName("admin-create-red-shop");
        mav.addObject("redThemeListInfos", redthemeListInfos);
//        mav.addObject("greenThemeListInfos", greenthemeListInfos);
        return mav;
    }

    @RequestMapping(value = "/registerGreenShop", method = RequestMethod.GET)
    public ModelAndView getregisterGreenShop(){
        ModelAndView mav = new ModelAndView();
//        List<Theme> redthemeListInfos;
        List<Theme> greenthemeListInfos;
//        redthemeListInfos = themeService.getThemeTags(0);
        greenthemeListInfos = themeService.getThemeTags(1);
        mav.setViewName("admin-create-green-shop");
//        mav.addObject("redThemeListInfos", redthemeListInfos);
        mav.addObject("greenThemeListInfos", greenthemeListInfos);
        return mav;
    }


    @RequestMapping(value ="/shop/{shopType}", method= RequestMethod.GET)
    public ModelAndView getShopList(@PathVariable String shopType){
        ModelAndView mav = new ModelAndView();
        if(shopType.equals("red")){
            shopType = "0";
            mav.setViewName("admin-shop-red-list");
        }else{
            shopType = "1";
            mav.setViewName("admin-shop-green-list");
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


        return mav;
    }

    @RequestMapping(value ={"/shop/{shopType}/{shopId}","/{shopId}/{path}"}, method= RequestMethod.GET)
    public ModelAndView getShopDetail(@PathVariable String shopType, @PathVariable Integer shopId, @PathVariable Optional<Integer> path){

        ModelAndView mav = new ModelAndView();
        if(shopType.equals("red")){
            shopType = "0";
            mav.setViewName("admin-shop-red-detail");
        }else{
            shopType = "1";
        }
        List<Shop> commentList;
        List<HashTag> hashTags;
        List<Theme> themeTags;
        List<Region> regionInfosWithType3;

        themeTags = themeService.getThemeTags(Integer.parseInt(shopType));
        commentList = shopService.getShopDetail(shopId);
        hashTags = tagService.getHashTags(shopId);
        regionInfosWithType3 = regionService.getRegionInfoWithType3("3");

//        List<String> themeList = Arrays.asList(commentList.get(0).split(","));

        int idx = path.orElseGet(()->{return 0;});
        int size = reviewService.getItemSizeByShopId(shopId);
        String url = "/shop/"+shopId+"/";

        mav.addObject("reviews", reviewService.getItemsByShopId(shopId, idx));
        mav.addObject("paginations", paginationService.getPagination(size, idx, reviewInterval, url));
        mav.addObject("btnUrls", paginationService.getPaginationBtn(size, idx, reviewInterval, url));
        mav.addObject("payload", commentList);
        mav.addObject("regionInfosWithType3", regionInfosWithType3);
        mav.addObject("hashTags",hashTags);
        mav.addObject("themeTags", themeTags);

        return mav;
    }


    @ResponseBody
    @RequestMapping(value ="/category/{type}/{id}", method= RequestMethod.GET)
    public List<FoodCategory> getCategorySelectInfos(Model model, @PathVariable String type, @PathVariable Integer id){
        List<FoodCategory> categoryInfoList = null;
        if(type.equals("mfood")){
            categoryInfoList = foodCategoryAdminService.getAdminRegionMCategory(id);
        }else if (type.equals("sfood")){
            categoryInfoList = foodCategoryAdminService.getAdminRegionSCategory(id);
        }
        return categoryInfoList;
    }

    @ResponseBody
    @RequestMapping(value ="/region/{provinceName}", method= RequestMethod.GET)
    public List<Region> getRegionDistrictInfo(Model model, @PathVariable String provinceName){
        List<Region> regionInfos = regionAdminService.getRegionDistrictInfo(provinceName);
        return regionInfos;
    }
    @ResponseBody
    @RequestMapping(value ="/region/type/2/{provinceName}", method= RequestMethod.GET)
    public List<String> getRegionDistrictInfoWithRegionType2(Model model, @PathVariable String provinceName){
        List<String> regionInfos = regionAdminService.getRegionDistrictInfoWithRegionType2(provinceName);
        return regionInfos;
    }

    @ResponseBody
    @RequestMapping(value ="/region/type/2/{provinceName}/{districtName}", method= RequestMethod.GET)
    public List<Region> getRegionSubwayInfoWithRegionType2(Model model, @PathVariable String provinceName, @PathVariable String districtName){
        List<Region> regionInfos = regionAdminService.getRegionSubwayInfoWithRegionType2(districtName, provinceName);
        return regionInfos;
    }

    @ResponseBody
    @RequestMapping(value ="/category/{type}/movePlusOne/{id}", method= RequestMethod.PUT)
    public List<FoodCategory> moveOne(Model model, @PathVariable String type, @PathVariable Integer id){
        List<FoodCategory> categoryInfoList = null;

        if(type.equals("mfood")){
            categoryInfoList = foodCategoryAdminService.getAdminRegionMCategory(id);
        }else if (type.equals("sfood")){
            categoryInfoList = foodCategoryAdminService.getAdminRegionSCategory(id);
        }else if(type.equals("bfood")){
            categoryInfoList = foodCategoryAdminService.updateSeqPlusOne(1, id);
        }
        return categoryInfoList;
    }

    @ResponseBody
    @RequestMapping(value ="/category/{type}/moveMinusOne/{id}", method= RequestMethod.PUT)
    public List<FoodCategory> moveMinusOne(Model model, @PathVariable String type, @PathVariable Integer id){
        List<FoodCategory> categoryInfoList = null;

        if(type.equals("mfood")){
            categoryInfoList = foodCategoryAdminService.getAdminRegionMCategory(id);
        }else if (type.equals("sfood")){
            categoryInfoList = foodCategoryAdminService.getAdminRegionSCategory(id);
        }else if(type.equals("bfood")){
            categoryInfoList = foodCategoryAdminService.updateSeqMinusOne(1, id);
        }
        return categoryInfoList;
    }

    @ResponseBody
    @RequestMapping(value ={"/category/{shopType}/filter"}, method= RequestMethod.GET)
    public List<Shop> filterWithCategory(Model model, @PathVariable String shopType, @RequestParam Integer bCode, @RequestParam Integer mCode, @RequestParam Integer sCode, @RequestParam Integer regionId){
        List<Shop> payload = null;
        if(shopType.equals("red")){
            shopType = "0";
        }else{
            shopType = "1";
        }

        if(regionId != 0){
            if(bCode != 0 && mCode != 0 && sCode != 0) {
                payload = shopService.getAdminShopInfosWithBcodeAndMcodeAndScodeWithRegionId(bCode, mCode, sCode, shopType, regionId);
            }else if (bCode != 0 && mCode != 0 && sCode == 0){
                payload = shopService.getAdminShopInfosWithBcodeAndMcodeWithRegionId(bCode, mCode, shopType, regionId);
            }else if (bCode != 0 && mCode == 0 && sCode == 0){
                payload = shopService.getAdminShopInfosWithBcodeWithRegionId(bCode, shopType, regionId);
            }
        }else{
            if(bCode != 0 && mCode != 0 && sCode != 0) {
                payload = shopService.getAdminShopInfosWithBcodeAndMcodeAndScode(bCode, mCode, sCode, shopType);
            }else if (bCode != 0 && mCode != 0 && sCode == 0){
                payload = shopService.getAdminShopInfosWithBcodeAndMcode(bCode, mCode, shopType);
            }else if (bCode != 0 && mCode == 0 && sCode == 0){
                payload = shopService.getAdminShopInfosWithBcode(bCode, shopType);
            }
        }

//        if(type.equals("mfood")){
//            categoryInfoList = foodCategoryAdminService.getAdminRegionMCategory(id);
//        }else if (type.equals("sfood")){
//            categoryInfoList = foodCategoryAdminService.getAdminRegionSCategory(id);
//        }else if(type.equals("bfood")){
//            categoryInfoList = foodCategoryAdminService.updateSeqMinusOne(1, id);
//        }
        return payload;
    }

    @ResponseBody
    @RequestMapping(value ={"/category/{shopType}/all"}, method= RequestMethod.GET)
    public Map<String, List> getAllCategory(Model model, @PathVariable String shopType){
        if(shopType.equals("red")){
            shopType = "0";
        }else{
            shopType = "1";
        }
        List<Shop> commentList;
        commentList = shopService.getAdminShopInfos(shopType);
        Map<String, List> members = new HashMap<>();
        members.put("data", commentList);
        return members;
    }

}
