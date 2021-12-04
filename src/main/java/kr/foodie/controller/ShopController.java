package kr.foodie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.domain.shopItem.HashTag;

import kr.foodie.domain.shopItem.Region;
import kr.foodie.domain.shopItem.Shop;
import kr.foodie.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RequiredArgsConstructor
@Controller
public class ShopController {

    private static final int shopInterval = 16;
    private static final int reviewInterval = 6;

    private final ShopService shopService;
    private final FoodCategoryService foodCategoryService;
    private final TagService tagService;
    private final RegionService regionService;
    private final ReviewService reviewService;
    private final PaginationService paginationService;
    private final ThemeService themeService;

    @GetMapping({"/shop/region/{regionId}/{shopType}", "/shop/region/{regionId}/{shopType}/{path}"})
    public String getCategory(@PathVariable Integer regionId, @PathVariable String shopType,
                              @PathVariable Optional<String> path, Model model) {

        String shopTypeId = shopType.equals("red") ? "0" : "1";
        int idx = Integer.parseInt(path.orElseGet(() -> {
            return "0";
        }));
        Integer area1st =0;
        Integer area2st =0;
        Integer area3st =0;
        int size = shopService.getItemSizeByRegionTypeAndShopType(area1st,  area2st,  area3st, shopTypeId);

//        model.addAttribute("regionInfo", regionService.getRegionInfo(area1st,  area2st,  area3st));
        model.addAttribute("priority", shopService.getShopPremiumInfos(area1st,  area2st,  area3st, shopTypeId));
        model.addAttribute("sidePriority", shopService.getShopInfosWithSideOrder(area1st,  area2st,  area3st, shopTypeId));
        model.addAttribute("themeList", themeService.getThemeTags("red_theme"));

        //pagination
        model.addAttribute("payload", shopService.getShopInfos(area1st, area2st,area3st, shopTypeId, idx, shopInterval));
        model.addAttribute("paginations", paginationService.getPagination(size, idx, shopInterval, "/shop/region/" + regionId + "/" + shopType + "/"));
        model.addAttribute("btnUrls", paginationService.getPaginationBtn(size, idx, shopInterval, "/shop/region/" + regionId + "/" + shopType + "/"));

        return shopType.equals("red") ? "submain-red" : "submain-green";
    }

    @GetMapping({"/shop/region/subway/{regionId}/{shopType}", "region/{regionId}/{shopType}/{path}"})
    public String getCategory2(@PathVariable Integer regionId, @PathVariable String shopType,
                               @PathVariable Optional<String> path, Model model) throws JsonProcessingException {

        String shopTypeId = shopType.equals("red") ? "0" : "1";
        int idx = Integer.parseInt(path.orElseGet(() -> {
            return "0";
        }));
        Integer area1st =0;
        Integer area2st =0;
        Integer area3st =0;
        int size = shopService.getItemSizeByRegionTypeAndShopType(area1st,  area2st,  area3st, shopTypeId);

        model.addAttribute("payload", shopService.getSubwayShopInfos(area1st, area2st, area3st, shopTypeId, idx, shopInterval));
//        model.addAttribute("regionInfo", regionService.getRegionInfo(area1st,  area2st,  area3st));
        model.addAttribute("priority", shopService.getSubwayPremiumShopInfos(area1st, area2st, area3st, shopTypeId));
        model.addAttribute("sidePriority", shopService.getShopInfosWithSideOrder(area1st, area2st, area3st, shopTypeId));
        model.addAttribute("themeList", themeService.getThemeTags("red_theme"));
        model.addAttribute("paginations", paginationService.getPagination(size, idx, shopInterval, "/shop/region/" + regionId + "/" + shopType + "/"));
        model.addAttribute("btnUrls", paginationService.getPaginationBtn(size, idx, shopInterval, "/shop/region/" + regionId + "/" + shopType + "/"));

        return shopType.equals("red") ? "submain-red" : "submain-green";
    }

    @GetMapping(value = "/shop")
    public ModelAndView getShopDetail(@RequestParam(value = "id") Integer shopId,
                                      @RequestParam(value = "page") Integer page,
                                      @AuthenticationPrincipal AuthUserDetails userDetails) {
        ModelAndView mav = new ModelAndView();

        Shop commentList;
        List<HashTag> hashTags;
        commentList = shopService.getShopDetail(shopId);
        hashTags = tagService.getHashTags(shopId);

        int idx = page;
        int size = reviewService.getItemSizeByShopId(shopId);
        String url = "/shop?id=" + shopId + "&page=";

        //category
        String bCode = Optional.ofNullable(commentList.getBigCategory()).orElseGet(()->{return "0";});
        String mCode = Optional.ofNullable(commentList.getMiddleCategory()).orElseGet(()->{return "0";});

        //tasteRating
        String tasteRatingInfo = reviewService.getShopTasteRatingAVG(shopId);
        String[] str = tasteRatingInfo.split(",");
        commentList.setFoodieLogRating(str[0]);

        //logRating
        commentList.setFoodieLogRating(Optional.ofNullable(commentList.getFoodieLogRating())
                .orElseGet(()->{return "없음";}));

        //menu imgaes
        String hasMenuImages = Optional.ofNullable(commentList.getMenuImages()).orElseGet(() -> {return "0";});
        if(!hasMenuImages.equals("0"))
            commentList.setMenuImages(commentList.getMenuImages().replace("[", "").replace("]", "").replaceAll("\"",""));
        else
            commentList.setMenuImages("");

        mav.addObject("tasteRatingCnt", str[1]);
        mav.addObject("category", foodCategoryService.getShopCategory(bCode, mCode, commentList.getAddress()));
        mav.addObject("reviews", reviewService.getItemsByShopId(shopId, idx));
        mav.addObject("paginations", paginationService.getPagination(size, idx, reviewInterval, url));
        mav.addObject("btnUrls", paginationService.getPaginationBtn(size, idx, reviewInterval, url));
        mav.addObject("payload", commentList);

        mav.addObject("userId", (userDetails != null) ? userDetails.getUser().getId() : 0);
        mav.addObject("userRole", (userDetails != null) ? userDetails.getUser().getRoleType() : "GENERAL");

            Integer background = commentList.getShopType();
            if (background == 1 ) {
                mav.setViewName("detail-red");
            } else if (background == 2 ) {
                mav.setViewName("detail-green");
            } else if (background == 3 ) {
                mav.setViewName("detail-mustard");
            }else {
                mav.setViewName("detail-mint");
            }
            mav.addObject("hashTags", hashTags);
            return mav;
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