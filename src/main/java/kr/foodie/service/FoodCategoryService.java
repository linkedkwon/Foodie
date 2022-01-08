package kr.foodie.service;

import kr.foodie.repo.admin.EpicureRegionRepository;
import kr.foodie.repo.admin.FoodCategoryAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class FoodCategoryService {

    private final EpicureRegionRepository epicureRegionRepository;

    public String getShopCategory(String bCode, String mCode, String address){

        String category = "";
        bCode = bCode.toString().startsWith(",")?bCode:bCode;
        mCode = mCode.toString().startsWith(",")?mCode:mCode;

        bCode = bCode.toString().contains(",")?bCode :bCode;
        mCode = mCode.toString().contains(",")?mCode:mCode;


        category += bCode.toString().equalsIgnoreCase("0")? "":"" + epicureRegionRepository.findByCode(Integer.parseInt(bCode)).get(0).getListName();
        category += mCode.toString().equalsIgnoreCase("0")? "":" | " + epicureRegionRepository.findByCode(Integer.parseInt(mCode)).get(0).getListName();

        return category;
    }

}
