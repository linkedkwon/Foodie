package kr.foodie.controller;

import com.google.gson.Gson;
//import kr.foodie.domain.account.ReviewAdmin;
import kr.foodie.common.CommonResponse;
import kr.foodie.domain.category.Category;
import kr.foodie.domain.category.Theme;
import kr.foodie.domain.shopItem.*;
import kr.foodie.service.*;
import kr.foodie.service.admin.FoodCategoryAdminService;
import kr.foodie.service.admin.RegionAdminService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
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
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final ShopTownService shopTownService;
    private static final List<String> regionType = new ArrayList<>(Arrays.asList("red_shop_type", "area_type", "subway_type","green_theme","red_theme", "town", "green_shop_type"));

    static boolean isStringEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String[] parseAdminShopType(String shopType){
        String type = null;
        String templateName = null;
        String templateFormat = null;
        if (shopType.equals("red")) {
            type = "1";
            templateName = "레드리스트1";
            templateFormat = "admin-shop-red-list";
        } else if (shopType.equals("mustard")) {
            type = "3";
            templateName = "레드리스트2";
            templateFormat = "admin-shop-red-list";
        } else if (shopType.equals("green")) {
            type = "2";
            templateName = "그린리스트1";
            templateFormat = "admin-shop-green-list";
        } else if (shopType.equals("allgreen")) {
            type = "allgreen";
            templateName = "전체 그린리스트";
            templateFormat = "admin-shop-green-list";
        } else if (shopType.equals("allred")) {
            type = "allred";
            templateName = "전체 레드리스트";
            templateFormat = "admin-shop-red-list";
        } else if (shopType.equals("mint")) {
            type = "4";
            templateName = "그린리스트2";
            templateFormat = "admin-shop-green-list";
        }

        return new String[] {type, templateName, templateFormat};
    }

    public static String[] parseAdminRegionEnvShopType(String categoryType){
        String type = null;
        String templateFormat;
        if(categoryType.equals("subway")){
            type= regionType.get(2);
            templateFormat = "admin-register-env-subway";
        }else if(categoryType.equals("foodVillage")) {
            type= regionType.get(5);
            templateFormat = "admin-register-env-foodVillage";
        }else if(categoryType.equals("theme")) {
            type = regionType.get(4);
            templateFormat = "admin-register-env-theme";
        }else if(categoryType.equals("red")){
            type= regionType.get(0);
            templateFormat = "admin-register-env-red";
        }else if(categoryType.equals("green")){
            type= regionType.get(6);
            templateFormat = "admin-register-env-green";
        }else {
            type= regionType.get(1);
            templateFormat = "admin-register-env-region";
        }

        return new String[] {type, templateFormat};
    }

    @RequestMapping(value = "/registerEnv/{categoryType}", method = RequestMethod.GET)
    public ModelAndView getRegisterRegionEnv(@PathVariable String categoryType) {
        ModelAndView mav = new ModelAndView();
        String[] result = parseAdminRegionEnvShopType(categoryType);
        String type = result[0];
        mav.setViewName(result[1]);
        if(categoryType.equals("theme")){
            List<Theme> redthemeListInfos = themeService.getThemeTags("red_theme");
            List<Theme> greenthemeListInfos = themeService.getThemeTags("green_theme");
            mav.addObject("redthemeListInfos", redthemeListInfos);
            mav.addObject("greenthemeListInfos", greenthemeListInfos);
        }else if(categoryType.equals("foodVillage")){
            List<ShopTown> regionsInfos = shopTownService.getAll(type);
            mav.addObject("regionInfos", regionsInfos);
        }else if(categoryType.equals("red") || categoryType.equals("green")){
            List<EpicureRegion> regionsInfos = regionAdminService.getEpicureFirstInfo(type);
            mav.addObject("regionInfos", regionsInfos);
        }else{
            List<EpicureRegion> regionsInfos = regionAdminService.getEpicureFirstInfo(type);
            mav.addObject("regionInfos", regionsInfos);
        }
        return mav;
    }


    /**
     * 여기하는중
     */

    @RequestMapping(value = "/registerRedShop", method = RequestMethod.GET)
    public ModelAndView getregisterRedShop(@ModelAttribute("shop") Shop shop) {
        ModelAndView mav = new ModelAndView();
        // 테마 카테고리
        List<Theme> redThemeListInfos = themeService.getThemeTags("red_theme");;
        // 음식 카테고리
        List<EpicureRegion> categoryInfos = regionAdminService.getEpicureFirstInfo("red_shop_type");

        //대분류, 지역류, 지하철류
        List<EpicureRegion> allFirstFoodInfos = null;
        List<EpicureRegion> allFirstRegionInfos = null;
        List<EpicureRegion> allFirstSubwayInfos = null;
        //맛집촌
        List<ShopTown> shopTownList = null;
        //테마리스트
        List<Theme> themeTags = null;



        List<EpicureRegion> secondFoodInfos = null;


        allFirstFoodInfos = regionAdminService.getEpicureFirstInfo("red_shop_type");
        allFirstRegionInfos = regionAdminService.getEpicureFirstInfo("area_type");
        allFirstSubwayInfos = regionAdminService.getEpicureFirstInfo("subway_type");

        themeTags = themeService.getThemeTags("red_theme");
        shopTownList = shopTownService.getAll("town");


        mav.addObject("allFirstFoodInfos", allFirstFoodInfos);
        mav.addObject("allFirstRegionInfos", allFirstRegionInfos);
        mav.addObject("allFirstSubwayInfos", allFirstSubwayInfos);

        mav.addObject("secondFoodInfos", secondFoodInfos);


        mav.addObject("shop", Shop.emptyShop());
        mav.addObject("categoryList", categoryInfos);
        mav.addObject("redThemeListInfos", redThemeListInfos);

        mav.addObject("themeTags", themeTags);
        mav.addObject("shopTownList", shopTownList);


        mav.setViewName("admin-create-red-shop");
        return mav;
    }



    @RequestMapping(value = "/registerGreenShop", method = RequestMethod.GET)
    public ModelAndView getRegisterGreenShop() {
        ModelAndView mav = new ModelAndView();

        // 테마 카테고리
        List<Theme> redThemeListInfos = themeService.getThemeTags("green_theme");;
        // 맛집촌 리스트
        List<ShopTown> shopTownList = shopTownService.getAll("town");

        List<EpicureRegion> allFirstFoodInfos = null;
        List<EpicureRegion> allFirstRegionInfos = null;
        List<EpicureRegion> allFirstSubwayInfos = null;

        allFirstFoodInfos = regionAdminService.getEpicureFirstInfo("green_shop_type");
        allFirstRegionInfos = regionAdminService.getEpicureFirstInfo("area_type");
        allFirstSubwayInfos = regionAdminService.getEpicureFirstInfo("subway_type");
        List<Theme> themeTags = null;
        themeTags = themeService.getThemeTags("green_theme");

        mav.addObject("shop", Shop.emptyShop());
        mav.addObject("allFirstFoodInfos", allFirstFoodInfos);
        mav.addObject("allFirstRegionInfos", allFirstRegionInfos);
        mav.addObject("allFirstSubwayInfos", allFirstSubwayInfos);
        mav.addObject("greenThemeListInfos", redThemeListInfos);
        mav.addObject("categoryInfos", shopTownList);
        mav.addObject("shopTownList", shopTownList);
        mav.addObject("themeTags", themeTags);
        mav.setViewName("admin-create-green-shop");
        return mav;
    }

    @RequestMapping(value = "/shop/list/{shopType}", method = RequestMethod.GET)
    public ModelAndView getShopList(@PathVariable String shopType) {
        ModelAndView mav = new ModelAndView();
        if(shopType.equals("null")){
            return null;
        }
        List<Shop> shopList = null;
        List<EpicureRegion> categoryInfos = null;
        List<EpicureRegion> regionsInfos = regionAdminService.getEpicureFirstInfo("area_type");

        String[] result = parseAdminShopType(shopType);

        if(result[0].equals("allred")){
            shopList = shopService.getAllShopInfos(Arrays.asList(1,3));
            categoryInfos = regionAdminService.getEpicureFirstInfo("red_shop_type");
        }else if(result[0].equals("allgreen")){
            shopList = shopService.getAllShopInfos(Arrays.asList(2,4));
            categoryInfos = regionAdminService.getEpicureFirstInfo("green_shop_type");
        }

        String shopBackground = result[0];
        mav.setViewName(result[2]);
        Integer btype = null;
        // 리스트 호출 (레드, 그린, 레드2, 그린2, 전체 레드, 전체 그린)
        if(shopBackground.equals("1") | shopBackground.equals("3")){
            btype = Integer.parseInt(shopBackground);
            categoryInfos = regionAdminService.getEpicureFirstInfo("red_shop_type");
            shopList = shopService.getTop50AdminShopInfos(Arrays.asList(btype));
        }else if(shopBackground.equals("2") | shopBackground.equals("4")) {
            btype = Integer.parseInt(shopBackground);
            categoryInfos = regionAdminService.getEpicureFirstInfo("green_shop_type");
            shopList = shopService.getTop50AdminShopInfos(Arrays.asList(btype));
        }



        mav.addObject("payload", shopList);
        mav.addObject("categoryInfos", categoryInfos);
        mav.addObject("regionInfos", regionsInfos);
        mav.addObject("background", result[1]);
        mav.addObject("shopBackground", shopBackground);

        return mav;
    }

    //여기 하는중
    @RequestMapping(value = {"/shop/{shopType}/{shopId}", "/{shopId}/{path}"}, method = RequestMethod.GET)
    public ModelAndView getShopDetail(@PathVariable String shopType, @PathVariable Integer shopId, @PathVariable Optional<Integer> path) {
        ModelAndView mav = new ModelAndView();
        List<Theme> themeTags = null;

        List<HashTag> hashTags = tagService.getHashTags(shopId);
        List<Category> categoryList = categoryService.getCategory("3", "서울");
        List<EpicureRegion> categoryInfos = null;
        List<EpicureRegion> firstRegionInfos = null;
        List<EpicureRegion> secondRegionInfos = null;
        List<EpicureRegion> thirdRegionInfos = null;
        List<EpicureRegion> firstFoodInfos = null;
        List<EpicureRegion> secondFoodInfos = null;
        List<EpicureRegion> thirdFoodInfos = null;
        List<EpicureRegion> allFirstFoodInfos = null;
        List<EpicureRegion> allFirstRegionInfos = null;
        List<ShopTown> shopTownList = null;
        List<EpicureRegion> firstSubwayInfos = null;
        List<EpicureRegion> secondSubwayInfos = null;
        List<EpicureRegion> thirdSubwayInfos = null;
        List<EpicureRegion> allFirstSubwayInfos = null;


        Shop detailInfo = shopService.getShopDetail(shopId);
//        String[] subway2thArray = detailInfo.getSubway2st().split(",");
//        List<String> list = new ArrayList<String>(Arrays.asList(subway2thArray));
//        String subway2th = list.get(0);
//        String otherSubway2th = list.remove(0);
//        detailInfo.set=
        String categoryShopType = null;
        if (shopType.equals("red")) {
            mav.setViewName("admin-shop-red-detail");
            themeTags = themeService.getThemeTags("red_theme");
            categoryInfos = regionAdminService.getEpicureFirstInfo("red_shop_type");
            categoryShopType = "red_shop_type";
        } else {
            mav.setViewName("admin-shop-green-detail");
            themeTags = themeService.getThemeTags("green_theme");
            categoryInfos = regionAdminService.getEpicureFirstInfo("green_shop_type");
            categoryShopType = "green_shop_type";
        }
        if(isStringEmpty(detailInfo.getBigCategory())){
            firstFoodInfos = null;
        }else{
            firstFoodInfos = regionAdminService.getRegionFirstInfoByRegionId(Integer.parseInt(detailInfo.getBigCategory()), categoryShopType);
        }

        if(isStringEmpty(detailInfo.getMiddleCategory())){
            secondFoodInfos = null;
        }else{
            secondFoodInfos = regionAdminService.getEpicureDistrict(Integer.parseInt(detailInfo.getBigCategory()), categoryShopType);
        }

        if(isStringEmpty(detailInfo.getSmallCategory())){
            thirdFoodInfos = null;
        }else{
            thirdFoodInfos = regionAdminService.getRegionSecondInfoByRegionId(Integer.parseInt(detailInfo.getSmallCategory()), categoryShopType);
        }

        if(isStringEmpty(detailInfo.getSubwayTypeId())){
            firstSubwayInfos = null;
        }else{
            firstSubwayInfos = regionAdminService.getRegionFirstInfoByRegionId(Integer.parseInt(detailInfo.getSubwayTypeId()), "subway_type");
        }

        if(isStringEmpty(detailInfo.getSubway2st())){
            secondSubwayInfos = null;
        }else{
            secondSubwayInfos = regionAdminService.getRegionSecondInfoByRegionId(Integer.parseInt(detailInfo.getSubway2st()), "subway_type");
        }

        if(isStringEmpty(detailInfo.getSubway3st())){
            thirdSubwayInfos = null;
        }else{
            thirdSubwayInfos = regionAdminService.getRegionSecondInfoByRegionId(Integer.parseInt(detailInfo.getSubway3st()), "subway_type");
        }

        allFirstRegionInfos = regionAdminService.getEpicureFirstInfo("area_type");
        allFirstFoodInfos = regionAdminService.getEpicureFirstInfo(categoryShopType);
        allFirstSubwayInfos = regionAdminService.getEpicureFirstInfo("subway_type");

        firstRegionInfos = regionAdminService.getRegionFirstInfoByRegionId(detailInfo.getArea1st(), "area_type");
        secondRegionInfos = regionAdminService.getRegionSecondInfoByRegionId(detailInfo.getArea2st(), "area_type");
        thirdRegionInfos = regionAdminService.getRegionSecondInfoByRegionId(detailInfo.getArea3st(), "area_type");

        shopTownList = shopTownService.getAll("town");

        if (detailInfo.getMenuImages() != null) {
            detailInfo.setMenuImages(detailInfo.getMenuImages().replace("[", "").replace("]", "").replaceAll("\"", ""));
        } else {
            detailInfo.setMenuImages("[]");
        }

        //category
        String bCode = Optional.ofNullable(detailInfo.getBigCategory()).orElseGet(() -> {
            return "0";
        });
        String mCode = Optional.ofNullable(detailInfo.getMiddleCategory()).orElseGet(() -> {
            return "0";
        });
        String sCode = Optional.ofNullable(detailInfo.getSmallCategory()).orElseGet(() -> {
            return "0";
        });

        HashMap<String, String> map = new HashMap<String, String>();
        map = foodCategoryAdminService.getShopCategoryNameCode(bCode, mCode, sCode, categoryShopType);
        int idx = path.orElseGet(() -> {
            return 0;
        });
        int size = reviewService.getItemSizeByShopId(shopId);
        String url = "/shop/" + shopId + "/";

        mav.addObject("reviews", reviewService.getItemsByShopId(shopId, idx));
        mav.addObject("paginations", paginationService.getPagination(size, idx, reviewInterval, url));
        mav.addObject("btnUrls", paginationService.getPaginationBtn(size, idx, reviewInterval, url));
        mav.addObject("categoryList", categoryList);
        mav.addObject("categoryInfos", categoryInfos);
        mav.addObject("hashTags", hashTags);

        mav.addObject("allFirstRegionInfos", allFirstRegionInfos);
        mav.addObject("allFirstFoodInfos", allFirstFoodInfos);
        mav.addObject("allFirstSubwayInfos", allFirstSubwayInfos);

        mav.addObject("firstRegionInfos", firstRegionInfos);
        mav.addObject("secondRegionInfos", secondRegionInfos);
        mav.addObject("thirdRegionInfos", thirdRegionInfos);

        mav.addObject("firstFoodInfos", firstFoodInfos);
        mav.addObject("secondFoodInfos", secondFoodInfos);
        mav.addObject("thirdFoodInfos", thirdFoodInfos);

        mav.addObject("firstSubwayInfos", firstSubwayInfos);
        mav.addObject("secondSubwayInfos", secondSubwayInfos);
        mav.addObject("thirdSubwayInfos", thirdSubwayInfos);

        mav.addObject("shopTownList", shopTownList);
        mav.addObject("themeTags", themeTags);
        List<Theme> existThemeInfos = new ArrayList<>();
        List<Theme> extractThemeInfos = new ArrayList<>();
        List<String> items = new ArrayList<>();

        if(detailInfo.getThemeList() != null) {
            items = Arrays.asList(detailInfo.getThemeList().split(","));
        }

        for(int i=0; i<themeTags.size(); i++){
            if(items.contains(themeTags.get(i).getListName())){
                existThemeInfos.add(themeTags.get(i));
            }else{
                extractThemeInfos.add(themeTags.get(i));
            }
        }

        mav.addObject("existThemeInfos", existThemeInfos);
        mav.addObject("extractThemeInfos", extractThemeInfos);

        if(detailInfo.getMenuImages().equals("")|| detailInfo.getMenuImages().equals("[]")){
            mav.addObject("imageSize", -1);
        }else{
            int imageSize = detailInfo.getMenuImages().split(",").length;
            if(imageSize ==1){
                mav.addObject("imageSize", 0);
            }else{
                mav.addObject("imageSize", imageSize-1);
            }
            detailInfo.setShopImage(detailInfo.getMenuImages().split(",")[0]);
        }
        mav.addObject("shop", detailInfo);
        mav.addObject("payload", detailInfo);

        return mav;
    }

    @ResponseBody
    @PostMapping("/epicure/region")
    public CommonResponse<String> createRegion(@RequestBody List<RegionCreateDTO> param) {
        regionAdminService.update(param);
        return CommonResponse.<String>builder()
                .message("정상입니다.")
                .build();
    }
    @ResponseBody
    @PostMapping("/epicure/theme")
    public CommonResponse<String> createTheme(@RequestBody List<RegionCreateDTO> param) {
        themeService.update(param);
        return CommonResponse.<String>builder()
                .message("정상입니다.")
                .build();
    }

    @ResponseBody
    @RequestMapping(value = "/epicure/{type}/{parentNo}", method = RequestMethod.GET)
    public Map<String, List> getEpicureDistrict(Model model, @PathVariable Integer parentNo, @PathVariable String type) {
        List<EpicureRegion> regionInfos = regionAdminService.getEpicureDistrict(parentNo, type);
        Map<String, List> infos = new HashMap<>();
        infos.put("data", regionInfos);
        return infos;
    }

    @ResponseBody
    @RequestMapping(value = {"/duplicated/{shopType}/{shopName}"}, method = RequestMethod.GET)
    public List<Shop> getDuplicatedInfos(Model model, @PathVariable String shopType, @PathVariable String shopName) {
        List<Integer> targetList = null;
        if (shopType.equals("red")) {
            targetList = Arrays.asList(1,3);
        } else {
            targetList = Arrays.asList(2,4);
        }

        List<Shop> duplicatedInfos;
        duplicatedInfos = shopService.getDuplicatedInfos(targetList, shopName.strip());

        for(int i=0; i<duplicatedInfos.size(); i++) {
            int imageSize = duplicatedInfos.get(i).getMenuImages().split(",").length;
            if (imageSize > 0) {
                duplicatedInfos.get(i).setShopImage(duplicatedInfos.get(i).getMenuImages().replace("[", "").replace("]", "").replaceAll("\"", "").split(",")[0]);
            }
        }
        return duplicatedInfos;
    }

    @ResponseBody
    @RequestMapping(value = {"/{shopType}/all"}, method = RequestMethod.GET)
    public Map<String, List> getAllCategory(Model model, @PathVariable String shopType) {
        List<Shop> commentList = null;

        if (shopType.equals("allred")) {
            commentList = shopService.getTop50AdminShopInfos(Arrays.asList(1,3));
        } else if (shopType.equals("allgreen")) {
            commentList = shopService.getTop50AdminShopInfos(Arrays.asList(2,4));
        } else if(shopType.equals("1")){
            commentList = shopService.getTop50AdminShopInfos(Arrays.asList(1));
        }else if(shopType.equals("2")){
            commentList = shopService.getTop50AdminShopInfos(Arrays.asList(2));
        }else if(shopType.equals("3")){
            commentList = shopService.getTop50AdminShopInfos(Arrays.asList(3));
        }else if(shopType.equals("4")){
            commentList = shopService.getTop50AdminShopInfos(Arrays.asList(4));
        }

        for(int i=0; i<commentList.size(); i++){
            if (commentList.get(i).getMenuImages() != null) {
                commentList.get(i).setMenuImages(commentList.get(i).getMenuImages().replace("[", "").replace("]", "").replaceAll("\"", "").split(",")[0]);
            } else {
                commentList.get(i).setMenuImages("[]");
            }
        }

        Map<String, List> members = new HashMap<>();
        members.put("data", commentList);
        return members;
    }

    @PostMapping(value = "/shop/add/{shopType}")
    public String addShop(@ModelAttribute Shop shop, @PathVariable String shopType, MultipartFile[] files) {
        String viewName = null;
        Integer background = null;
        try {
            System.out.println("fff");

            if (shopType.equals("red")) {
                viewName = "redirect:/admin/shop/list/red";
            } else {
                viewName = "redirect:/admin/shop/list/green";
            }

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
            if (files.length > 0) {
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
        } catch (Exception ex) {
        }

        if(shop.getArea1st() == null){
            shop.setArea1st(null);
        }
        if(isStringEmpty(shop.getBigCategory())){
            shop.setBigCategory(null);
        }
        if(isStringEmpty(shop.getSubwayTypeId())){
            shop.setSubwayTypeId(null);
        }
        shopService.addShopInfo(shop);

        return viewName;

    }

    @PostMapping(value = "/shop/update/{shopId}")
    public String updateShop(@ModelAttribute ShopDTO shop,@PathVariable Integer shopId, @RequestParam("files") MultipartFile[] files) {
        System.out.println("fff");
        if(isStringEmpty(shop.getSubway2st())){
            shop.setSubway2st(null);
        }
        if(isStringEmpty(shop.getSubwayTypeId())){
            shop.setSubwayTypeId(null);
        }
        if(isStringEmpty(shop.getSubway3st())){
            shop.setSubway3st(null);
        }

        String viewName = shopService.updateShopInfo(shop, shopId, files, "aa");
        return viewName;
    }

    @RequestMapping(value = {"/shop/delete/{shopType}/{shopId}"}, method = RequestMethod.GET)
    public String deleteShop(Model model, @PathVariable Integer shopId, @PathVariable String shopType) {
        String viewName;
        shopService.deleteShop(shopId);
        if (shopType.equals("red")) {
            viewName = "redirect:/admin/shop/list/red";
        } else {
            viewName = "redirect:/admin/shop/list/green";
        }
        return viewName;
    }


    @PostMapping("/shop/image/update/{shopId}")
//    public String updateImages(@ModelAttribute ShopDTO shop, @RequestParam("files") MultipartFile[] files, @PathVariable Integer shopId, @RequestBody ExistImages existImages) {
        public String updateImages(@ModelAttribute ShopDTO shop, @RequestParam("files") MultipartFile[] files, @PathVariable Integer shopId, MultipartHttpServletRequest mtfRequest) {
        if(isStringEmpty(shop.getSubway2st())){
            shop.setSubway2st(null);
        }
        if(isStringEmpty(shop.getSubwayTypeId())){
            shop.setSubwayTypeId(null);
        }
        if(isStringEmpty(shop.getSubway3st())){
            shop.setSubway3st(null);
        }
        String existImages = ((StandardMultipartHttpServletRequest) mtfRequest).getRequest().getParameter("images");
        String viewName = shopService.updateShopInfo(shop, shopId, files, existImages);
        return viewName;
    }


    //area1~3 : 각각 시,구,도
    //shop1~3 : bCode, mCode, sCode
    //selectedOption : 선택 검색어 필터(업소명, 등록자..) keyword : 해당 키워드
    //couponFlag : 쿠폰 여부
    @GetMapping("/shop/list/filter")
    public Map<String, List> getAdminShopList(@RequestParam String shopType,
                                              @RequestParam String area1, @RequestParam String area2, @RequestParam String area3,
                                              @RequestParam String shop1, @RequestParam String shop2, @RequestParam String shop3,
                                              @RequestParam String selectedOption, @RequestParam String keyword, @RequestParam String couponFlag){

        Map<String, List> members = new HashMap<>();

        members.put("data", shopService.getAdminShopList(shopType, area1, area2, area3,
                                                            shop1, shop2, shop3,
                                                            selectedOption, keyword,
                                                            couponFlag));
        return members;
    }

    @RequestMapping(value = "/recommand/main/region", method = RequestMethod.GET)
    public ModelAndView getRecommandMainRegion() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin-recommand-main-region");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = {"/recommand/main/regionInfo/{shopType}"}, method = RequestMethod.GET)
    public Map<String, List> getRecommandMainRegionInfo(Model model, @PathVariable Integer shopType) {
        List<Shop> commentList;
        commentList = shopService.getShopInfoByType(shopType);
        Map<String, List> members = new HashMap<>();
        members.put("data", commentList);
        return members;
    }

    @ResponseBody
    @RequestMapping(value = {"/recommand/main/regionInfo/{type}/address/{address}"}, method = RequestMethod.GET)
    public Map<String, List> getRecommandMainRegionInfoWithAddress(Model model, @PathVariable String address, @PathVariable Integer type) {
        List<Shop> commentList;
        commentList = shopService.getShopInfoByAddressName(address, type);
        Map<String, List> members = new HashMap<>();
        members.put("data", commentList);
        return members;
    }

    @ResponseBody
    @RequestMapping(value = {"/recommand/update/main/regionInfo/address/{addressId}/shop/{shopId}"}, method = RequestMethod.POST)
    public Map<String, List> updateRecommandMainRegionInfoWithAddress(Model model, @PathVariable Integer addressId, @PathVariable Integer shopId) {
        List<Shop> commentList;
        List<Shop> currentList;
        Map<String, List> members = new HashMap<>();
        currentList = shopService.getShopInfoByType(addressId);
        if (currentList.size() > 7) {
            members.put("data", null);
        } else {
            commentList = shopService.insertShopInfo(shopId, addressId);
            members.put("data", commentList);
        }
        return members;
    }

    @ResponseBody
    @RequestMapping(value = {"/recommand/update/main/regionInfo/address/{addressId}/shop/{shopId}"}, method = RequestMethod.DELETE)
    public Map<String, List> deleteRecommandMainRegionInfoWithAddress(Model model, @PathVariable Integer addressId, @PathVariable Integer shopId) {
        List<Shop> commentList;
        List<Shop> currentList;
        Map<String, List> members = new HashMap<>();
        commentList = shopService.deleteShopInfo(shopId, addressId);
        members.put("data", commentList);
        return members;
    }

    @RequestMapping(value = "/recommand/other/region", method = RequestMethod.GET)
    public ModelAndView getRecommandOtherRegion() {
        ModelAndView mav = new ModelAndView();
//        mav.addObject("payload", shopService.getShopInfoByType(18));
        mav.setViewName("admin-recommand-other-region");
        return mav;
    }

    @RequestMapping(value = "/recommand/best/red", method = RequestMethod.GET)
    public ModelAndView getRecommandBestRedRegion() {
        ModelAndView mav = new ModelAndView();
//        mav.addObject("payload", shopService.getShopInfoByType(18));
        mav.setViewName("admin-recommand-best-red-region");
        return mav;
    }

    @RequestMapping(value = "/recommand/best/green", method = RequestMethod.GET)
    public ModelAndView getRecommandBestGreenRegion() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin-recommand-best-green-region");
        return mav;
    }

    @RequestMapping(value = "/comment/all", method = RequestMethod.GET)
    public ModelAndView getAllComment() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin-comment-list");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = {"/comment/all/info"}, method = RequestMethod.GET)
    public Map<String, List> getAllCommentInfo(Model model) {
        Map<String, List> members = new HashMap<>();
        members.put("data", reviewService.getAllReviews());
        return members;
    }



    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public ModelAndView getUserListPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin-user-list");
        return mav;
    }

}