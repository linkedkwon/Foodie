package kr.foodie.controller;

import kr.foodie.domain.shopItem.EpicureRegion;
import kr.foodie.domain.shopItem.Shop;
import kr.foodie.service.ShopService;
import kr.foodie.service.admin.RegionAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class MainController {

    private final ShopService shopService;
    private final RegionAdminService regionAdminService;
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

    @GetMapping({"","/"})
    public ModelAndView renderMain(Model model){
//        model.addAttribute("redCommentList", shopService.getShopInfoByType(18));
//        model.addAttribute("greenCommentList", shopService.getShopInfoByType(19));
        ModelAndView mav = new ModelAndView();
        List<EpicureRegion> regionsInfos = regionAdminService.getEpicureFirstInfo("area_type");
        for(int i=0; i<regionsInfos.size(); i++){
            regionsInfos.get(i).setListName(regionsInfos.get(i).getListName().replace("특별시", "").replace("광역시", "")
                    .replace("충청남도", "충남")
                    .replace("충청북도", "충북")
                    .replace("경상남도", "경남")
                    .replace("경상북도", "경북")
                    .replace("전라남도", "전남")
                    .replace("전라북도", "전북")
                    .replace("도", ""));
        }
        mav.addObject("regionInfos", regionsInfos);
        mav.addObject("redCommentList", shopService.getShopMainInfoByType(18));
        mav.addObject("greenCommentList", shopService.getShopMainInfoByType(19));
        mav.setViewName("main");
        return mav;
    }

    @ResponseBody
    @GetMapping(value ="/main/region/{regionType}")
    public List<Shop> getMainShopList(@PathVariable Integer regionType){
        return shopService.getShopMainInfoByType(regionType);
    }
}
