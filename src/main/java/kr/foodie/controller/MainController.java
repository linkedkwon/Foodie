package kr.foodie.controller;

import kr.foodie.domain.shop.Shop;
import kr.foodie.service.MainBoardService;
import kr.foodie.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class MainController {

    private final MainBoardService mainBoardService;
    private final ShopService shopService;

    @GetMapping({"","/"})
    public ModelAndView renderMain(Model model){
        ModelAndView mav = new ModelAndView();
        model.addAttribute("hi", "hello");

        List<Shop> redCommentList;
        List<Shop> greedCommentList;
        redCommentList = shopService.getShopInfoByType(18);
        greedCommentList = shopService.getShopInfoByType(19);
        model.addAttribute("redCommentList", redCommentList);
        model.addAttribute("greenCommentList", greedCommentList);
        mav.setViewName("main");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value ="/main/region/{regionType}", method= RequestMethod.GET)
    public List<Shop> getMainShopList(Model model, @PathVariable Integer regionType){
        List<Shop> commentList;
        commentList = shopService.getShopInfoByType(regionType);
        model.addAttribute("infos", commentList);
        return commentList;
    }
}
