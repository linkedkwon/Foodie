package kr.foodie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.domain.shopItem.EpicureRegion;
import kr.foodie.domain.shopItem.HashTag;

import kr.foodie.domain.shopItem.Region;
import kr.foodie.domain.shopItem.Shop;
import kr.foodie.repo.admin.EpicureRegionRepository;
import kr.foodie.service.*;
import kr.foodie.service.admin.RegionAdminService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class ShopController {

    private static final int shopInterval = 16;
    private static final int reviewInterval = 6;

    private final ShopService shopService;
    private final FoodCategoryService foodCategoryService;
    private final TagService tagService;
    private final ReviewService reviewService;
    private final PaginationService paginationService;
    private final EpicureRegionRepository epicureRegionRepository;
    private final ThemeService themeService;
    private final RegionAdminService regionAdminService;
    static boolean isStringEmpty(String str) {
        return str == null || str.isEmpty();
    }
    @GetMapping({"/shop/region/{regionId}/{shopType}", "/shop/region/{regionId}/{shopType}/{path}"})
    public String getCategory(@PathVariable Integer regionId, @PathVariable String shopType,
                              @PathVariable Optional<String> path, Model model) {
        List<Integer> type = null;
        List<Integer> sideType = null;
        String templateFormat = null;
        Integer area2st =regionId;
        int idx = Integer.parseInt(path.orElseGet(() -> {
            return "0";
        }));
        int size =0;
        if (shopType.equals("red")) {
            type = Arrays.asList(1,3);
            sideType = Arrays.asList(2,4);
            templateFormat = "submain-red";
            size = shopService.getItemSizeByRegionTypeAndShopType(false, area2st, type);
            model.addAttribute("themeList", themeService.getThemeTags("red_theme"));
            model.addAttribute("payload", shopService.getShopInfos(area2st, type, false, idx, shopInterval));
            model.addAttribute("sidePriority", shopService.getShopInfosWithSideOrder(false, area2st, sideType));
            model.addAttribute("priority", shopService.getShopPremiumInfos(false, area2st, type));
            // page 있는경우
            //model.addAttribute("priority", shopService.getShopPremiumInfos(idx, shopInterval, area2st, type));

        }else if (shopType.equals("green")) {
            type = Arrays.asList(2,4);
            sideType = Arrays.asList(1,3);
            templateFormat = "submain-green";
            area2st = epicureRegionRepository.findByCode(regionId).get(0).getParentNo();
            size = shopService.getItemSizeByRegionTypeAndShopType(true, area2st, type);
            model.addAttribute("themeList", themeService.getThemeTags("green_theme"));
            model.addAttribute("payload", shopService.getShopInfos(area2st, type, true, idx, shopInterval));
            model.addAttribute("sidePriority", shopService.getShopInfosWithSideOrder(true, area2st, sideType));
            model.addAttribute("priority", shopService.getShopPremiumInfos(true, area2st, type));
            // page 있는경우
            //model.addAttribute("priority", shopService.getShopPremiumInfos(idx, shopInterval, area2st, type));
        }




        String middleRegionName = epicureRegionRepository.findByCode(regionId).get(0).getListName();
        Integer middleRegionId = epicureRegionRepository.findByCode(regionId).get(0).getId();
        Integer bigRegionId = epicureRegionRepository.findByCode(regionId).get(0).getParentNo();
        String bigRegionName = epicureRegionRepository.findByCode(bigRegionId).get(0).getListName();
        model.addAttribute("bigRegionName", bigRegionName);
        model.addAttribute("middleRegionName", middleRegionName);

        model.addAttribute("regionId", regionId);
        model.addAttribute("paginations", paginationService.getPagination(size, idx, shopInterval, "/shop/region/" + regionId + "/" + shopType + "/"));
        model.addAttribute("btnUrls", paginationService.getPaginationBtn(size, idx, shopInterval, "/shop/region/" + regionId + "/" + shopType + "/"));
        return templateFormat;
    }

    @GetMapping({"/shop/region/subway/{regionId}/{shopType}", "region/{regionId}/{shopType}/{path}"})
    public String getCategory2(@PathVariable Integer regionId, @PathVariable String shopType,
                               @PathVariable Optional<String> path, Model model) throws JsonProcessingException {

        List<Integer> type = null;
        List<Integer> sideType = null;
        String templateFormat = null;
        Boolean isGreen = false;
        Integer area2st =regionId;
        int size = 0;
        if (shopType.equals("red")) {
            type = Arrays.asList(1,3);
            sideType = Arrays.asList(2,4);
            templateFormat = "submain-red";
            size = shopService.getItemSizeByRegionTypeAndShopType(isGreen, area2st, type);
        }else if (shopType.equals("green")) {
            type = Arrays.asList(2,4);
            sideType = Arrays.asList(1,3);
            templateFormat = "submain-green";
            isGreen = true;
            shopService.getItemSizeByRegionTypeAndShopType(isGreen, area2st, type);
        }

        int idx = Integer.parseInt(path.orElseGet(() -> {
            return "0";
        }));
        Integer area1st =0;
        Integer area3st =0;


        model.addAttribute("payload", shopService.getSubwayShopInfos(area1st, area2st, area3st, 1, idx, shopInterval));
//        model.addAttribute("regionInfo", regionService.getRegionInfo(area1st,  area2st,  area3st));
        model.addAttribute("priority", shopService.getSubwayPremiumShopInfos(area1st, area2st, area3st,1));
        model.addAttribute("sidePriority", shopService.getShopInfosWithSideOrder(isGreen, area2st, sideType));
        model.addAttribute("themeList", themeService.getThemeTags("red_theme"));
        model.addAttribute("paginations", paginationService.getPagination(size, idx, shopInterval, "/shop/region/" + regionId + "/" + shopType + "/"));
        model.addAttribute("btnUrls", paginationService.getPaginationBtn(size, idx, shopInterval, "/shop/region/" + regionId + "/" + shopType + "/"));


        return templateFormat;
    }

    // TODO 외주
    @GetMapping(value = "/shop")
    public ModelAndView getShopDetail(@RequestParam(value = "id") Integer shopId,
                                      @RequestParam(value = "page") Integer page,
                                      @AuthenticationPrincipal AuthUserDetails userDetails) {
        ModelAndView mav = new ModelAndView();

        Shop commentList;
        commentList = shopService.getShopDetail(shopId);

        shopService.updateHit(commentList.getShopId(), commentList.getFoodieHit());
        List<String> list = new ArrayList<String>();
        String[] hash = null;
        if(commentList.getTag() != null) {
             hash = commentList.getTag().split("#");
            if(hash.length == 1){
                hash = commentList.getTag().split(" ");
            }
            for(int i=0; i<hash.length; i++){
                if(!(isStringEmpty(hash[i]))){
                    list.add(hash[i]);
                }
            }
        }

        int idx = page;
        int size = reviewService.getItemSizeByShopId(shopId);
        String url = "/shop?id=" + shopId + "&page=";

        //category
        String bCode;
        String mCode;
        String sCode;
        if(isStringEmpty(commentList.getBigCategory())){
            bCode = "0";
        }else{
            bCode = Optional.ofNullable(commentList.getBigCategory()).orElseGet(()->{return "0";});
            String[] bCodeList = bCode.split(",");
            if(bCodeList.length > 1){
                bCode = bCodeList[0];
            }
        }
        if(isStringEmpty(commentList.getMiddleCategory())){
            mCode = "0";
        }else{
            mCode = Optional.ofNullable(commentList.getMiddleCategory()).orElseGet(()->{return "0";});
            String[] mCodeList = mCode.split(",");
            if(mCodeList.length > 1){
                mCode = mCodeList[0];
            }
        }
        if(isStringEmpty(commentList.getSmallCategory())){
            sCode = "0";
        }else{
            sCode = Optional.ofNullable(commentList.getSmallCategory()).orElseGet(()->{return "0";});
            String[] sCodeList = sCode.split(",");
            if(sCodeList.length > 1){
                sCode = sCodeList[0];
            }
        }

        //tasteRating
        String tasteRatingInfo = reviewService.getShopTasteRatingAVG(shopId);
        String[] str = tasteRatingInfo.split(",");
//        commentList.setTasteRatingInfo(str[0]);

        //logRating
        commentList.setFoodieLogRating(Optional.ofNullable(commentList.getFoodieLogRating())
                .orElseGet(()->{return "없음";}));
        if(commentList.getFoodieLogRating().equals("")){
            commentList.setFoodieLogRating("없음");
        }
        commentList.setFoodieLogRating(Optional.ofNullable(commentList.getFoodieLogRating())
                .orElseGet(()->{return "없음";}));


        //shop image
        mav.addObject("shopImage",extractShopImage(commentList));

        //menu images
        commentList.setMenuImages(extractMenuImages(commentList));

        //insta keyword
        mav.addObject("instaKeyword",commentList.getShopName().replaceAll("\\(.+\\)", "").strip());

        //buga
        if(isStringEmpty(commentList.getBuga())){
            commentList.setBuga("없음");
        }

        mav.addObject("tasteRatingCnt", str[1]);
        mav.addObject("category", foodCategoryService.getShopCategory(bCode, mCode, sCode, commentList.getAddress()));
        mav.addObject("reviews", reviewService.getItemsByShopId(shopId, idx));
        mav.addObject("paginations", paginationService.getPagination(size, idx, reviewInterval, url));
        mav.addObject("btnUrls", paginationService.getPaginationBtn(size, idx, reviewInterval, url));
        mav.addObject("payload", commentList);
        mav.addObject("hash", list);
        mav.addObject("userId", (userDetails != null) ? userDetails.getUser().getId() : 0);
        mav.addObject("userRole", (userDetails != null) ? userDetails.getUser().getRoleType() : "PREMIUM_90");

        Integer background = commentList.getShopType();
        if (background == 1 ) {
            mav.setViewName("detail-red");
        } else if (background == 2 ) {
            mav.setViewName("detail-green");
        } else if (background == 3 ) {
            mav.setViewName("detail-mustard");
        }else {
            mav.setViewName("detail-blue");
        }



        return mav;
    }

    private String extractShopImage(Shop commentList) {
        if(commentList.getShopImage() != null){
            return commentList.getShopImage();
        }
        String menuImgStr = commentList.getMenuImages();
        String replacedImg = null;
        if(commentList.getShopImage() != null){
            replacedImg = commentList.getShopImage().strip();
            return replacedImg;
        }

        if (StringUtils.hasText(menuImgStr)) {
            String[] images = menuImgStr
                    .replace("[", "")
                    .replace("]", "")
                    .replaceAll("\"", "")
                    .split(",");

            return Arrays.stream(images)
                    .findFirst()
                    .orElse(replacedImg);
        }

        return replacedImg;
    }

    private String extractMenuImages(Shop commentList) {
        String menuImgStr = commentList.getMenuImages();

        if (StringUtils.hasText(menuImgStr)) {
            String[] menuImages = menuImgStr
                    .replace("[", "")
                    .replace("]", "")
                    .replaceAll("\"", "")
                    .split(",");

//            return Arrays.stream(menuImages).skip(1L).collect(Collectors.joining(","));
            return Arrays.stream(menuImages).collect(Collectors.joining(","));
        }

        return Strings.EMPTY;
    }


    @GetMapping(value ="/filter/Allshop")
    public String filterAllShopInfos(@RequestParam(value = "shopType") String shopType,
                                     @RequestParam(value = "filter") String filterItems,
                                     @RequestParam(value = "pagingIdx", required = false) String pagingIdx, Model model) {

        int idx = Integer.parseInt(Optional.ofNullable(pagingIdx)
                                            .orElseGet(()->{return "0";}));
        String shopTypeId = shopType.equals("red") ? "0" : "1";

        Map<String, Object> bundle = shopService.getSearchListByKeyword(filterItems, shopTypeId, shopInterval, idx);
        int size = (int) bundle.get("size");
        String url = "/filter/Allshop?filter=" + filterItems + "&shopType=" + shopType + "&pagingIdx=";

        model.addAttribute("payload", (List<Shop>)bundle.get("payload"));
        model.addAttribute("keyword", filterItems);
        model.addAttribute("paginations", paginationService.getPagination(size, idx, shopInterval, url));
        model.addAttribute("btnUrls", paginationService.getPaginationBtn(size, idx, shopInterval, url));

        return shopType.equals("red") ? "search-submain-red" : "search-submain-green";
    }


    @ResponseBody
    @GetMapping(value ="/filter/shop")
    public Map<String, List> filterShopInfos(@RequestParam(value = "regionId") Integer regionId, @RequestParam(value = "regionType") String regionType, @RequestParam(value = "filter") String filterItems, Model model) {
        Map<String, List> results = new HashMap<>();
        String shopTypeId = regionType.equals("red") ? "0" : "1";
        List<Shop> filterList;
        List<Shop> resultFilterList = new ArrayList<>();
        String[] items = filterItems.split(",");
        Integer area1st =0;
        Integer area2st =0;
        Integer area3st =0;

        if(items.length > 0) {
            filterList = shopService.getFilterShopList(shopTypeId, area1st, area2st, area3st, items[0]);
            if (items.length > 1) {
                for (int i = 1; i < items.length; i++) {
                    for (int j = 0; j < filterList.size(); j++) {
                        if (filterList.get(j).getThemeList().contains(items[i])) {
                            resultFilterList.add(filterList.get(j));
                        }
                    }
                }
                results.put("payload", resultFilterList);
            }else{
                filterList = shopService.getFilterShopList(shopTypeId, area1st, area2st, area3st, items[0]);
                results.put("payload", filterList);
            }
        }else{
            filterList = shopService.getFilterShopList(shopTypeId, area1st, area2st, area3st, null);
            results.put("payload", filterList);
        }

        return results;
    }

    @GetMapping({"/shop/location/{lat}/{lng}/{shopType}"})
    public String getShopListWithLocation(@PathVariable String lat, @PathVariable String lng, @PathVariable String shopType, Model model){
        if (shopType.equals("red")) {
            shopType = "0";
        } else {
            shopType = "1";
        }
        List<Shop> commentList;
        List<Region> regionInfos = new ArrayList<>();

        Region region = new Region();
        region.setDistrictName("2KM");
        region.setProvinceName("범위");
        regionInfos.add(region);

        commentList = shopService.getShopInfoByAddress(lat, lng, shopType);
        model.addAttribute("payload", commentList);
        model.addAttribute("regionInfo", regionInfos);
        model.addAttribute("location", lat + "/" + lng);

        return shopType.equals("red") ? "location-submain-red" : "location-submain-green";
    }

    @ResponseBody
    @GetMapping("/shop/report")
    public String inquiryReport(@AuthenticationPrincipal AuthUserDetails userDetails){
        return userDetails == null ? "0" : "1";
    }
}
