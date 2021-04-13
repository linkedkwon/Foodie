package kr.foodie.controller;

import kr.foodie.domain.shop.HashTag;
import kr.foodie.domain.shop.Shop;
import kr.foodie.service.PaginationService;
import kr.foodie.service.RegionService;
import kr.foodie.service.ShopService;
import kr.foodie.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/shop")
public class ShopController {

    private static final int interval = 14;

    private final ShopService shopService;
    private final TagService tagService;
    private final RegionService regionService;
    private final PaginationService paginationService;


    public ShopController(ShopService shopService, TagService tagService,
                          RegionService regionService, PaginationService paginationService) {
        this.shopService = shopService;
        this.tagService = tagService;
        this.regionService = regionService;
        this.paginationService = paginationService;
    }

    @GetMapping({"region/{regionTypeId}/{shopType}", "region/{regionTypeId}/{shopType}/{path}"})
    public ModelAndView getCategory(@PathVariable String regionTypeId, @PathVariable String shopType,
                                    @PathVariable Optional<String> path){

        String shopTypeId = shopType.equals("red")? "0" : "1";
        int idx = Integer.parseInt(path.orElseGet(()->{return "0";}));
        int size = shopService.getItemSizeByRegionTypeAndShopType(regionTypeId, shopTypeId);
        System.out.println("사이즈에요:"+size);
        ModelAndView mav = new ModelAndView();

        mav.addObject("payload", shopService.getShopInfos(regionTypeId, shopTypeId, idx, interval));
        mav.addObject("regionInfo", regionService.getRegionInfo(Integer.valueOf(regionTypeId)));
        mav.addObject("priority", shopService.getShopInfosWithOrder(regionTypeId, shopTypeId, 9));
        mav.addObject("sidePriority", shopService.getShopInfosWithSideOrder(regionTypeId, shopTypeId, 8));

        mav.addObject("paginations", paginationService.getPagination(size, idx,interval,"/shop/region/"+regionTypeId+"/"+shopType+"/"));
        mav.addObject("btnUrls", paginationService.getPaginationBtn(size, idx, interval, "/shop/region/"+regionTypeId+"/"+shopType+"/"));

        if(shopType.equals("red"))
            mav.setViewName("submain-red");
        else
            mav.setViewName("submain-green");
        return mav;
    }

    @RequestMapping(value ="/{shopId}", method= RequestMethod.GET)
    public ModelAndView getShopDetail(@PathVariable Integer shopId){
        ModelAndView mav = new ModelAndView();

        List<Shop> commentList;
        List<HashTag> hashTags;
        commentList = shopService.getShopDetail(shopId);
        hashTags = tagService.getHashTags(shopId);
        mav.addObject("payload", commentList);
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
}
