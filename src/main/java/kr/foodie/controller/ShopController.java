package kr.foodie.controller;

import kr.foodie.config.security.auth.AuthUserDetails;
import kr.foodie.domain.shop.HashTag;

import kr.foodie.domain.shop.Region;
import kr.foodie.domain.shop.Shop;
import kr.foodie.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/shop/")
public class ShopController {

    private static final int shopInterval = 14;
    private static final int reviewInterval = 6;

    private final ShopService shopService;
    private final TagService tagService;
    private final RegionService regionService;
    private final ReviewService reviewService;
    private final PaginationService paginationService;

    @GetMapping({"region/{regionTypeId}/{shopType}", "region/{regionTypeId}/{shopType}/{path}"})
    public String getCategory(@PathVariable String regionTypeId, @PathVariable String shopType,
                              @PathVariable Optional<String> path, Model model){

        String shopTypeId = shopType.equals("red")? "0" : "1";
        int idx = Integer.parseInt(path.orElseGet(()->{return "0";}));
        int size = shopService.getItemSizeByRegionTypeAndShopType(regionTypeId, shopTypeId);

        model.addAttribute("payload", shopService.getShopInfos(regionTypeId, shopTypeId, idx, shopInterval));
        model.addAttribute("regionInfo", regionService.getRegionInfo(Integer.valueOf(regionTypeId)));
        model.addAttribute("priority", shopService.getShopInfosWithOrder(regionTypeId, shopTypeId, 9));
        model.addAttribute("sidePriority", shopService.getShopInfosWithSideOrder(regionTypeId, shopTypeId, 8));

        model.addAttribute("paginations", paginationService.getPagination(size, idx,shopInterval,"/shop/region/"+regionTypeId+"/"+shopType+"/"));
        model.addAttribute("btnUrls", paginationService.getPaginationBtn(size, idx, shopInterval, "/shop/region/"+regionTypeId+"/"+shopType+"/"));

        return shopType.equals("red")? "submain-red":"submain-green";
    }

    @GetMapping(value ={"/{shopId}", "/{shopId}/{path}"})
    public ModelAndView getShopDetail(@PathVariable Integer shopId, @PathVariable Optional<Integer> path,
                                      @AuthenticationPrincipal AuthUserDetails userDetails){
        ModelAndView mav = new ModelAndView();

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
        mav.addObject("userId", (userDetails != null)? userDetails.getUser().getId():0);
        mav.addObject("userRole", (userDetails != null)? userDetails.getUser().getRoleType():"GENERAL");

        if(commentList.size() > 0){
            Integer background = commentList.get(0).getBackground();
            if(background == 1){
                mav.setViewName("detail-green");
            }else if (background == 2){
                mav.setViewName("detail-red");
            }else{
                mav.setViewName("detail-mustard");
            }
            mav.addObject("hashTags",hashTags);
            return mav;
        }
        return null;
    }

    @RequestMapping(value ="/location/{lat}/{lng}/{shopType}", method= RequestMethod.GET)
    public ModelAndView getShopListWithLocation(@PathVariable String lat, @PathVariable String lng, @PathVariable String shopType){
        ModelAndView mav = new ModelAndView();
        if(shopType.equals("red")){
            shopType = "0";
        }else{
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
        mav.addObject("location", lat+"/"+lng);
        if(shopType.equals("0")){
            mav.setViewName("location-submain-red");
        }else{
            mav.setViewName("location-submain-green");
        }
        return mav;
    }
}
