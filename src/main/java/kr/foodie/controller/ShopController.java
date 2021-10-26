package kr.foodie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.domain.shop.HashTag;

import kr.foodie.domain.shop.Region;
import kr.foodie.domain.shop.Shop;
import kr.foodie.repo.ShopRepository;
import kr.foodie.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
        int size = shopService.getItemSizeByRegionTypeAndShopType(regionId, shopTypeId);

        model.addAttribute("regionInfo", regionService.getRegionInfo(Integer.valueOf(regionId)));
        model.addAttribute("priority", shopService.getShopPremiumInfos(regionId, shopTypeId));
        model.addAttribute("sidePriority", shopService.getShopInfosWithSideOrder(regionId, shopTypeId));
        model.addAttribute("themeList", themeService.getThemeTags(Integer.valueOf(shopTypeId)));

        //pagination
        model.addAttribute("payload", shopService.getShopInfos(regionId, shopTypeId, idx, shopInterval));
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
        int size = shopService.getItemSizeByRegionTypeAndShopType(regionId, shopTypeId);

        model.addAttribute("payload", shopService.getSubwayShopInfos(regionId, shopTypeId, idx, shopInterval));
        model.addAttribute("regionInfo", regionService.getRegionInfo(Integer.valueOf(regionId)));
        model.addAttribute("priority", shopService.getSubwayPremiumShopInfos(regionId, shopTypeId));
        model.addAttribute("sidePriority", shopService.getShopInfosWithSideOrder(regionId, shopTypeId));
        model.addAttribute("themeList", themeService.getThemeTags(Integer.valueOf(shopTypeId)));
        model.addAttribute("paginations", paginationService.getPagination(size, idx, shopInterval, "/shop/region/" + regionId + "/" + shopType + "/"));
        model.addAttribute("btnUrls", paginationService.getPaginationBtn(size, idx, shopInterval, "/shop/region/" + regionId + "/" + shopType + "/"));

        return shopType.equals("red") ? "submain-red" : "submain-green";
    }

    @GetMapping(value = "/shop")
    public ModelAndView getShopDetail(@RequestParam(value = "id") Integer shopId,
                                      @RequestParam(value = "page") Integer page,
                                      @AuthenticationPrincipal AuthUserDetails userDetails) {
        ModelAndView mav = new ModelAndView();

        List<Shop> commentList;
        List<HashTag> hashTags;
        commentList = shopService.getShopDetail(shopId);
        hashTags = tagService.getHashTags(shopId);

        int idx = page;
        int size = reviewService.getItemSizeByShopId(shopId);
        String url = "/shop?id=" + shopId + "&page=";

        //category
        String bCode = Optional.ofNullable(commentList.get(0).getBigCategory()).orElseGet(()->{return "0";});
        String mCode = Optional.ofNullable(commentList.get(0).getMiddleCategory()).orElseGet(()->{return "0";});

        //tasteRating
        String tasteRatingInfo = reviewService.getShopTasteRatingAVG(shopId);
        String[] str = tasteRatingInfo.split(",");
        commentList.get(0).setTasteRating(str[0]);

        //logRating
        commentList.get(0).setFoodieLogRating(Optional.ofNullable(commentList.get(0).getFoodieLogRating())
                .orElseGet(()->{return "없음";}));

        //menu imgaes
        commentList.get(0).setMenuImages(commentList.get(0).getMenuImages().replace("[", "").replace("]", "").replaceAll("\"",""));

        mav.addObject("tasteRatingCnt", str[1]);
        mav.addObject("category", foodCategoryService.getShopCategory(bCode, mCode, commentList.get(0).getAddress()));
        mav.addObject("reviews", reviewService.getItemsByShopId(shopId, idx));
        mav.addObject("paginations", paginationService.getPagination(size, idx, reviewInterval, url));
        mav.addObject("btnUrls", paginationService.getPaginationBtn(size, idx, reviewInterval, url));
        mav.addObject("payload", commentList);
        mav.addObject("userId", (userDetails != null) ? userDetails.getUser().getId() : 0);
        mav.addObject("userRole", (userDetails != null) ? userDetails.getUser().getRoleType() : "GENERAL");

        if (commentList.size() > 0) {
            String background = commentList.get(0).getBackground();
            if (background == "1") {
                mav.setViewName("detail-green");
            } else if (background == "2") {
                mav.setViewName("detail-red");
            } else {
                mav.setViewName("detail-mustard");
            }
            mav.addObject("hashTags", hashTags);
            return mav;
        }
        return null;
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

        if(items.length > 0) {
            filterList = shopService.getFilterShopList(shopTypeId, regionId, items[0]);
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
                filterList = shopService.getFilterShopList(shopTypeId, regionId, items[0]);
                results.put("payload", filterList);
            }
        }else{
            filterList = shopService.getFilterShopList(shopTypeId, regionId, null);
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