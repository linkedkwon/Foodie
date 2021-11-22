package kr.foodie.controller;

import com.google.gson.Gson;
//import kr.foodie.domain.account.ReviewAdmin;
import kr.foodie.domain.category.Category;
import kr.foodie.domain.category.FoodCategory;
import kr.foodie.domain.category.Theme;
import kr.foodie.domain.shop.*;
import kr.foodie.service.*;
import kr.foodie.service.admin.FoodCategoryAdminService;
import kr.foodie.service.admin.RegionAdminService;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
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
    private final UserService userService;
    private final CategoryService categoryService;

    public AdminController(ShopService shopService, TagService tagService,
                           RegionAdminService regionAdminService, FoodCategoryAdminService foodCategoryAdminService,
                           ReviewService reviewService, PaginationService paginationService, ThemeService themeService, RegionService regionService, TagListService tagListService, UserService userService,CategoryService categoryService) {
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
        this.userService = userService;
        this.categoryService = categoryService;
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
            mav.setViewName("admin-register-env-red");
        }else{
            shopType = "1";
            mav.setViewName("admin-register-env-green");
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
//        mav.setViewName("admin-register-env");
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

    @ResponseBody
    @RequestMapping(value ={"/recommand/main/regionInfo/{type}/address/{address}"}, method= RequestMethod.GET)
    public Map<String, List> getRecommandMainRegionInfoWithAddress(Model model, @PathVariable String address, @PathVariable Integer type){
        List<Shop> commentList;
        commentList = shopService.getShopInfoByAddressName(address, type);
        Map<String, List> members = new HashMap<>();
        members.put("data", commentList);
        return members;
    }

    @ResponseBody
    @RequestMapping(value ={"/recommand/update/main/regionInfo/address/{addressId}/shop/{shopId}"}, method= RequestMethod.POST)
    public Map<String, List> updateRecommandMainRegionInfoWithAddress(Model model, @PathVariable Integer addressId, @PathVariable Integer shopId){
        List<Shop> commentList;
        List<Shop> currentList;
        Map<String, List> members = new HashMap<>();
        currentList = shopService.getShopInfoByType(addressId);
        if(currentList.size()>7){
            members.put("data", null);
        }else{
            commentList = shopService.insertShopInfo(shopId, addressId);
            members.put("data", commentList);
        }


        return members;
    }

    @ResponseBody
    @RequestMapping(value ={"/recommand/update/main/regionInfo/address/{addressId}/shop/{shopId}"}, method= RequestMethod.DELETE)
    public Map<String, List> deleteRecommandMainRegionInfoWithAddress(Model model, @PathVariable Integer addressId, @PathVariable Integer shopId){
        List<Shop> commentList;
        List<Shop> currentList;
        Map<String, List> members = new HashMap<>();
        commentList = shopService.deleteShopInfo(shopId, addressId);
        members.put("data", commentList);
        return members;
    }

    @RequestMapping(value = "/recommand/other/region", method = RequestMethod.GET)
    public ModelAndView getRecommandOtherRegion(){
        ModelAndView mav = new ModelAndView();
//        mav.addObject("payload", shopService.getShopInfoByType(18));
        mav.setViewName("admin-recommand-other-region");
        return mav;
    }

//    @ResponseBody
//    @RequestMapping(value ={"/recommand/other/regionInfo/{shopType}"}, method= RequestMethod.GET)
//    public Map<String, List> getRecommandOtherRegionInfo(Model model, @PathVariable Integer shopType){
//        List<Shop> commentList;
//        commentList = shopService.getShopInfoByType(shopType);
//        Map<String, List> members = new HashMap<>();
//        members.put("data", commentList);
//        return members;
//    }


    @RequestMapping(value = "/recommand/best/red", method = RequestMethod.GET)
    public ModelAndView getRecommandBestRedRegion(){
        ModelAndView mav = new ModelAndView();
//        mav.addObject("payload", shopService.getShopInfoByType(18));
        mav.setViewName("admin-recommand-best-red-region");
        return mav;
    }

//    @ResponseBody
//    @RequestMapping(value ={"/recommand/best/red/regionInfo/{shopType"}, method= RequestMethod.GET)
//    public Map<String, List> getRecommandBestRedRegionInfo(Model model, @PathVariable Integer shopType){
//        List<Shop> commentList;
//        commentList = shopService.getShopInfoByType(shopType);
//        Map<String, List> members = new HashMap<>();
//        members.put("data", commentList);
//        return members;
//    }

    @RequestMapping(value = "/recommand/best/green", method = RequestMethod.GET)
    public ModelAndView getRecommandBestGreenRegion(){
        ModelAndView mav = new ModelAndView();
//        mav.addObject("payload", shopService.getShopInfoByType(18));
        mav.setViewName("admin-recommand-best-green-region");
        return mav;
    }

    @RequestMapping(value = "/comment/all", method = RequestMethod.GET)
    public ModelAndView getAllComment(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin-comment-list");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value ={"/comment/all/info"}, method= RequestMethod.GET)
    public Map<String, List> getAllCommentInfo(Model model){
        Map<String, List> members = new HashMap<>();
        members.put("data", reviewService.getAllReviews());
        return members;
    }


//    @ResponseBody
//    @RequestMapping(value ={"/recommand/best/green/regionInfo/{shopType"}, method= RequestMethod.GET)
//    public Map<String, List> getRecommandBestGreenRegionInfo(Model model, @PathVariable Integer shopType){
//        List<Shop> commentList;
//        commentList = shopService.getShopInfoByType(shopType);
//        Map<String, List> members = new HashMap<>();
//        members.put("data", commentList);
//        return members;
//    }

    @RequestMapping(value = "/registerEnvRegion", method = RequestMethod.GET)
    public ModelAndView getregisterEnvRegion(){
        ModelAndView mav = new ModelAndView();
        List<EpicureRegion> regionsInfos;
        regionsInfos = regionAdminService.getEpicureProvince();
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
    public ModelAndView getregisterRedShop(@ModelAttribute("shop") Shop shop){
        ModelAndView mav = new ModelAndView();
        List<Theme> redthemeListInfos;
        List<FoodCategory> categoryInfos;
        mav.addObject("shop", Shop.emptyShop());
        List<Category> categoryList = categoryService.getCategory("3", "서울");
        categoryInfos = foodCategoryAdminService.getAdminRegionBCategory();
        redthemeListInfos = themeService.getThemeTags(0);
        mav.setViewName("admin-create-red-shop");
        mav.addObject("categoryList", categoryList);
        mav.addObject("redThemeListInfos", redthemeListInfos);
        mav.addObject("categoryInfos", categoryInfos);
//        mav.addObject("greenThemeListInfos", greenthemeListInfos);ㅁ
        return mav;
    }

    @RequestMapping(value = "/registerGreenShop", method = RequestMethod.GET)
    public ModelAndView getregisterGreenShop(){
        ModelAndView mav = new ModelAndView();
        List<Theme> greenthemeListInfos;
        List<FoodCategory> categoryInfos;
        greenthemeListInfos = themeService.getThemeTags(1);
        mav.addObject("shop", Shop.emptyShop());
        List<Category> categoryList = categoryService.getCategory("3", "서울");
        categoryInfos = foodCategoryAdminService.getAdminTripRegionBCategory();
        mav.addObject("categoryInfos", categoryInfos);
        mav.addObject("greenThemeListInfos", greenthemeListInfos);
        mav.setViewName("admin-create-green-shop");
//        mav.addObject("redThemeListInfos", redthemeListInfos);
        mav.addObject("categoryList", categoryList);

        mav.addObject("greenThemeListInfos", greenthemeListInfos);
        return mav;
    }

    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public ModelAndView getUserListPage(){
        ModelAndView mav = new ModelAndView();
//        List<Theme> redthemeListInfos;
//        List<Theme> greenthemeListInfos;
//        redthemeListInfos = themeService.getThemeTags(0);
//        greenthemeListInfos = themeService.getThemeTags(1);
        mav.setViewName("admin-user-list");
//        mav.addObject("redThemeListInfos", redthemeListInfos);
//        mav.addObject("greenThemeListInfos", greenthemeListInfos);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value ={"/user/{userType}/all"}, method= RequestMethod.GET)
    public Map<String, List> getUserList(@PathVariable String userType){
        if(userType.equals("0")){
            userType = "0";
        }else{
            userType = "1";
        }
//        List<User> userList;
//        userList = userService.getAllUserInfo(userType);
        Map<String, List> members = new HashMap<>();
        members.put("data", userService.getAllUserInfo(userType));
        return members;
    }

//    @RequestMapping(value ="/user/{userType}/all", method= RequestMethod.GET)
//    public ModelAndView getUserList(@PathVariable String userType){
//        ModelAndView mav = new ModelAndView();
//        if(userType.equals("0")){
//            userType = "0";
//            mav.setViewName("admin-user-list");
//        }else{
//            userType = "1";
//            mav.setViewName("admin-shop-green-list");
//        }
//        List<User> userList;
//        List<FoodCategory> categoryInfos;
//        List<Shop> sideCommentListWithOrder;
//        List<String> regionInfos;
//        regionInfos = regionAdminService.getRegionProvinceInfo();
//        categoryInfos = foodCategoryAdminService.getAdminRegionBCategory();
//        userList = userService.getAllUserInfo(userType);
//        commentListWithOrder = shopService.getShopInfosWithOrder(regionTypeId, shopType, 9);
//        sideCommentListWithOrder = shopService.getShopInfosWithSideOrder(regionTypeId, shopType, 8);
//        mav.addObject("payload", userList);
//        mav.addObject("categoryInfos", categoryInfos);
//        mav.addObject("regionInfos", regionInfos);
//        mav.addObject("regionInfo", regiaonInfos);
//        mav.addObject("priority", commentListWithOrder);
//        mav.addObject("sidePriority", sideCommentListWithOrder);
//        if(shopType.equals("0")){


//        return mav;
//    }

    @RequestMapping(value ="/shop/{shopType}", method= RequestMethod.GET)
    public ModelAndView getShopList(@PathVariable String shopType){
        ModelAndView mav = new ModelAndView();
        if(shopType.equals("red")){
            shopType = "0";
            mav.setViewName("admin-shop-red-list");
        }else if(shopType.equals("green")) {
            shopType = "2";
            mav.setViewName("admin-shop-green-list");
        }else if(shopType.equals("mustard")) {
            shopType = "3";
            mav.setViewName("admin-shop-mustard-list");
        }else{
            shopType = "4";
            mav.setViewName("admin-shop-mint-list");
        }
        List<EpicureRegion> regionsInfos;
        regionsInfos = regionAdminService.getEpicureProvince();
//        regions = regionAdminService.findRegionProvinceInfoAll();
//        categoryMInfos = foodCategoryAdminService.getAdminRegionMCategory(100);
//        categorySInfos = foodCategoryAdminService.getAdminRegionSCategory(1000);
        mav.addObject("regionInfos", regionsInfos);

        List<Shop> commentList;
        List<FoodCategory> categoryInfos;
//        List<Shop> sideCommentListWithOrder;
//        List<String> regionInfos;
//        regionInfos = regionAdminService.getRegionProvinceInfo();
        categoryInfos = foodCategoryAdminService.getAdminRegionBCategory();
        commentList = shopService.getTop50AdminShopInfos(shopType);
//        commentListWithOrder = shopService.getShopInfosWithOrder(regionTypeId, shopType, 9);
//        sideCommentListWithOrder = shopService.getShopInfosWithSideOrder(regionTypeId, shopType, 8);
        mav.addObject("payload", commentList);

        mav.addObject("categoryInfos", categoryInfos);
//        mav.addObject("regionInfos", regionInfos);
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
            mav.setViewName("admin-shop-green-detail");
        }
        Shop commentList;
        List<HashTag> hashTags;
        List<Theme> themeTags;
        List<Region> regionInfosWithType3;
        List<Region> regionInfosWithId;
        List<Region> subwayInfosWithId;
        themeTags = themeService.getThemeTags(Integer.parseInt(shopType));
        commentList = shopService.getShopDetail(shopId);
        hashTags = tagService.getHashTags(shopId);
        regionInfosWithType3 = regionService.getRegionInfoWithType3("3");

        if(commentList.getMenuImages() != null) {
            commentList.setMenuImages(commentList.getMenuImages().replace("[", "").replace("]", "").replaceAll("\"", ""));
        }else{
            commentList.setMenuImages("[]");
        }
//        List<String> themeList = Arrays.asList(commentList.get(0).split(","));

        //category
        String bCode = Optional.ofNullable(commentList.getBigCategory()).orElseGet(()->{return "0";});
        String mCode = Optional.ofNullable(commentList.getMiddleCategory()).orElseGet(()->{return "0";});
        String sCode = Optional.ofNullable(commentList.getSmallCategory()).orElseGet(()->{return "0";});

        HashMap<String, String> map = new HashMap<String, String>();
        map = foodCategoryAdminService.getShopCategoryNameCode(bCode, mCode, sCode);
        regionInfosWithId = regionAdminService.getRegionRegionInfo(commentList.getRegionId());
        subwayInfosWithId = regionAdminService.getRegionRegionInfo(commentList.getSubwayTypeId());
        int idx = path.orElseGet(()->{return 0;});
        int size = reviewService.getItemSizeByShopId(shopId);
        String url = "/shop/"+shopId+"/";
        mav.addObject("shop", commentList);
        mav.addObject("reviews", reviewService.getItemsByShopId(shopId, idx));
        mav.addObject("paginations", paginationService.getPagination(size, idx, reviewInterval, url));
        mav.addObject("adminCategory",map);
        mav.addObject("btnUrls", paginationService.getPaginationBtn(size, idx, reviewInterval, url));
        mav.addObject("payload", commentList);
        List<Category> categoryList = categoryService.getCategory("3", "서울");
        mav.addObject("categoryList", categoryList);
        mav.addObject("regionInfosWithId",regionInfosWithId);
        mav.addObject("subwayInfosWithId",subwayInfosWithId);
        List<FoodCategory> categoryInfos;

        categoryInfos = foodCategoryAdminService.getAdminTripRegionBCategory();
        mav.addObject("categoryInfos", categoryInfos);
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
    @RequestMapping(value ="/epicure/{parentNo}", method= RequestMethod.GET)
    public Map<String, List> getEpicureDistrict(Model model, @PathVariable Integer parentNo){
        List<EpicureRegion> regionInfos = regionAdminService.getEpicureDistrict(parentNo);
        Map<String, List> infos = new HashMap<>();
        infos.put("data", regionInfos);
        return infos;
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
    @RequestMapping(value ={"/duplicated/{shopType}/{shopName}"}, method= RequestMethod.GET)
    public List<Shop> getDuplicatedInfos(Model model, @PathVariable String shopType, @PathVariable String shopName){
        if(shopType.equals("red")){
            shopType = "0";
        }else{
            shopType = "1";
        }
        List<Shop> duplicatedInfos;
        duplicatedInfos = shopService.getDuplicatedInfos(shopType, shopName.strip());
//        mav.addObject("duplicatedInfos", duplicatedInfos);
//        Map<String, List> members = new HashMap<>();
//        members.put("data", commentList);
        return duplicatedInfos;
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
        commentList = shopService.getTop50AdminShopInfos(shopType);
        Map<String, List> members = new HashMap<>();
        members.put("data", commentList);
        return members;
    }
    @PostMapping(value = "/shop/add/{shopType}")
    public String addShop(Model model,
                          @ModelAttribute Shop shop, @PathVariable String shopType, MultipartFile[] files) {
        String viewName = null;
        try {
            System.out.println("fff");

            if(shopType.equals("red")){
                shopType = "0";
                viewName = "admin-shop-red-list";
            }else{
                shopType = "1";
                viewName = "admin-shop-green-list";
            }
            shop.setShopType(shopType);
            String server = "foodie.speedgabia.com";
            int port = 21;
            String user = "foodie";
            String pw = "a584472yscp@@";
            FTPClient con = null;
            Date from = new Date();
            SimpleDateFormat nowDateHHmmss = new SimpleDateFormat("HHmmss");
            SimpleDateFormat nowDateymd = new SimpleDateFormat("yyyyMMdd");
            String nowHHmmss = nowDateHHmmss.format(from);
            String nowymd = nowDateymd.format(from);
            ArrayList<String> images = new ArrayList<>();
            if(files.length > 0) {
                try {
                    con = new FTPClient();
                    con.setControlEncoding("utf-8");
                    con.connect(server);
                    if (con.login(user, pw)) {
                        con.enterLocalPassiveMode(); // important!
                        con.setFileType(FTP.BINARY_FILE_TYPE);

                        for (int i = 0; i < files.length; i++) {
                            if (!(files[i].getOriginalFilename().equals(""))) {
                                con.storeFile(files[i].getOriginalFilename(), files[i].getInputStream());
                                images.add("http://foodie.speedgabia.com/" + files[i].getOriginalFilename());
                            }
                        }

                        String result = new Gson().toJson(images);
                        shop.setMenuImages(result);
                        con.logout();
                        con.disconnect();
                    }
                } catch (Exception e) {
                }
            }
            shopService.addShopInfo(shop);

        } catch (Exception ex) {
        }

        return viewName;

    }

    @PostMapping(value = "/shop/update/{shopId}")
    public String updateShop(@ModelAttribute ShopDTO shop, @PathVariable Integer shopId, MultipartFile[] files) {
        System.out.println("fff");
        String viewName = shopService.updateShopInfo(shop, shopId);
        return viewName;
    }

    @GetMapping("/shop/list/filter")
    public Map<String, List> getAdminShopList(@RequestParam String shopType, @RequestParam String address,
                                        @RequestParam String bCode, @RequestParam String mCode, @RequestParam String sCode){
        Map<String, List> members = new HashMap<>();
        members.put("data", shopService.getAdminShopList(shopType, address, bCode, mCode, sCode));
        return members;
    }

}
