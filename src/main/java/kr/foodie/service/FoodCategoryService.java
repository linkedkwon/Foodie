package kr.foodie.service;

import kr.foodie.repo.admin.FoodCategoryAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class FoodCategoryService {

    private final FoodCategoryAdminRepository foodCategoryAdminRepository;

    public String getShopCategory(String bCode, String mCode, String address){

        String category = "";
        bCode = bCode.toString().startsWith(",")?bCode:bCode;
        mCode = mCode.toString().startsWith(",")?mCode:mCode;

        bCode = bCode.toString().contains(",")?bCode :bCode;
        mCode = mCode.toString().contains(",")?mCode:mCode;


//        category += bCode.toString().equalsIgnoreCase("0")? "":"" + foodCategoryAdminRepository.findByBCodeAndLevel(Integer.valueOf(bCode),1).get(0).getCategoryName();
//        category += mCode.toString().equalsIgnoreCase("0")? "":" | " + foodCategoryAdminRepository.findByMCodeAndLevel(Integer.valueOf(mCode),2).get(0).getCategoryName();

        return category;
    }

}
