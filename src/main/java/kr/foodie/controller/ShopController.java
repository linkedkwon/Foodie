package kr.foodie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.domain.shop.HashTag;

import kr.foodie.domain.shop.Region;
import kr.foodie.domain.shop.Shop;
import kr.foodie.service.*;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Controller
public class ShopController {

    private static final int shopInterval = 14;
    private static final int reviewInterval = 6;

    private final ShopService shopService;
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

        mav.addObject("reviews", reviewService.getItemsByShopId(shopId, idx));
        mav.addObject("paginations", paginationService.getPagination(size, idx, reviewInterval, url));
        mav.addObject("btnUrls", paginationService.getPaginationBtn(size, idx, reviewInterval, url));
        mav.addObject("payload", commentList);
        mav.addObject("userId", (userDetails != null) ? userDetails.getUser().getId() : 0);
        mav.addObject("userRole", (userDetails != null) ? userDetails.getUser().getRoleType() : "GENERAL");

        if (commentList.size() > 0) {
            Integer background = commentList.get(0).getBackground();
            if (background == 1) {
                mav.setViewName("detail-green");
            } else if (background == 2) {
                mav.setViewName("detail-red");
            } else {
                mav.setViewName("detail-mustard");
            }
            mav.addObject("hashTags", hashTags);
            return mav;
        }
        return null;
    }

    @ResponseBody
    @GetMapping(value ="/filter/shop")
    public Map<String, List> filterShopInfos(@RequestParam(value = "regionId") Integer regionId, @RequestParam(value = "regionType") String regionType, @RequestParam(value = "filter") String filterItems) {
        Map<String, List> results = new HashMap<>();
        String shopTypeId = regionType.equals("red") ? "0" : "1";
        List<Shop> filtertList;
        filtertList = shopService.getFilterShopList(shopTypeId, regionId, filterItems);
        results.put("payload", filtertList);

//        List<Shop> commentList;


//        mav.addObject("payload", commentList);
        return results;
    }
//    @GetMapping(value ="/filter/shop")
//    public ModelAndView filterShopInfos(@RequestParam(value = "regionId") Integer regionId, @RequestParam(value = "regionType") String regionType, @RequestParam(value = "filter") String filterItems) {
//        ModelAndView mav = new ModelAndView();
//        List<Shop> commentList;
//        String shopTypeId = regionType.equals("red") ? "0" : "1";
//        commentList = shopService.getFilterShopList(shopTypeId, regionId, filterItems);
//        mav.addObject("payload", commentList);
//
//        return regionType.equals("red") ? "submain-red" : "submain-green";
//    }


    @RequestMapping(value ="/location/{lat}/{lng}/{shopType}", method= RequestMethod.GET)
    public ModelAndView getShopListWithLocation(@PathVariable String lat, @PathVariable String lng, @PathVariable String shopType){
        ModelAndView mav = new ModelAndView();
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
        mav.addObject("payload", commentList);
        mav.addObject("regionInfo", regionInfos);
        mav.addObject("location", lat + "/" + lng);
        if (shopType.equals("0")) {
            mav.setViewName("location-submain-red");
        }else {
            mav.setViewName("location-submain-green");
        }
        return mav;
    }

    @ResponseBody
    @GetMapping("/shop/report")
    public String inquiryReport(@AuthenticationPrincipal AuthUserDetails userDetails){
        return userDetails == null ? "0" : "1";
    }
}