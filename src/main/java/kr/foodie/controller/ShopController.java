package kr.foodie.controller;

import kr.foodie.domain.shop.HashTag;
import kr.foodie.domain.shop.Shop;
import kr.foodie.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

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


    public ShopController(ShopService shopService, TagService tagService,
                          RegionService regionService, ReviewService reviewService,
                          PaginationService paginationService) {
        this.shopService = shopService;
        this.tagService = tagService;
        this.regionService = regionService;
        this.reviewService = reviewService;
        this.paginationService = paginationService;
    }

    @GetMapping({"region/{regionTypeId}/{shopType}", "region/{regionTypeId}/{shopType}/{path}"})
    public ModelAndView getCategory(@PathVariable String regionTypeId, @PathVariable String shopType,
                                    @PathVariable Optional<String> path){

        String shopTypeId = shopType.equals("red")? "0" : "1";
        int idx = Integer.parseInt(path.orElseGet(()->{return "0";}));
        int size = shopService.getItemSizeByRegionTypeAndShopType(regionTypeId, shopTypeId);

        ModelAndView mav = new ModelAndView();

        mav.addObject("payload", shopService.getShopInfos(regionTypeId, shopTypeId, idx, shopInterval));
        mav.addObject("regionInfo", regionService.getRegionInfo(Integer.valueOf(regionTypeId)));
        mav.addObject("priority", shopService.getShopInfosWithOrder(regionTypeId, shopTypeId, 9));
        mav.addObject("sidePriority", shopService.getShopInfosWithSideOrder(regionTypeId, shopTypeId, 8));

        mav.addObject("paginations", paginationService.getPagination(size, idx,shopInterval,"/shop/region/"+regionTypeId+"/"+shopType+"/"));
        mav.addObject("btnUrls", paginationService.getPaginationBtn(size, idx, shopInterval, "/shop/region/"+regionTypeId+"/"+shopType+"/"));

        if(shopType.equals("red"))
            mav.setViewName("submain-red");
        else
            mav.setViewName("submain-green");
        return mav;
    }

    @GetMapping({"/{shopId}","/{shopId}/{path}"})
    public ModelAndView getShopDetail(@PathVariable Integer shopId, @PathVariable Optional<Integer> path){
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
