package kr.foodie.controller;

import kr.foodie.domain.shopItem.AdminShopListVO;
import kr.foodie.domain.shopItem.Shop;
import kr.foodie.service.ReviewService;
import kr.foodie.service.admin.AdminShopListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class AdminListController {

    private final AdminShopListService adminShopListService;
    private final ReviewService reviewService;

    @ResponseBody
    @PostMapping("/item/list")
    public Map<String, Object> getAdminShopList(@ModelAttribute AdminShopListVO vo) {

        Map<String, Object> data = new HashMap<>();
        data.put("currentPage", vo.getPage() % 10 == 0 ? 9 : vo.getPage() % 10 - 1);
        data.put("count", adminShopListService.getCountList(vo));

        List<Shop> commentList = adminShopListService.getAdminShopList(vo);



        //이미지 문자 처리
        for(int i = 0; i < commentList.size(); i++){
            //tasteRating
            String tasteRatingInfo = reviewService.getShopTasteRatingAVG(commentList.get(i).getShopId());
            String[] str = tasteRatingInfo.split(",");

            if (commentList.get(i).getMenuImages() != null) {
                commentList.get(i).setMenuImages(commentList.get(i).getMenuImages().replace("[", "").replace("]", "").replaceAll("\"", "").split(",")[0]);
            } else {
                commentList.get(i).setMenuImages("[]");
            }
            if(str[1].equals("0")){
                commentList.get(i).setStar("");
            }else{
                commentList.get(i).setStar("/images/ico-point-" + str[1] + "-active.png");
            }
        }
        data.put("data", commentList);
        data.put("pages", adminShopListService.getPages(vo));
        data.put("btns", adminShopListService.getBtnPages(vo));

        return data;
    }
}