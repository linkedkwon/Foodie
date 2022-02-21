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

    public String getShopCategory(String bCode, String mCode, String sCode, String address){

        String category = "";
        bCode = bCode.startsWith(",")?bCode:bCode;
        mCode = mCode.startsWith(",")?mCode:mCode;
        sCode = sCode.startsWith(",")?sCode:sCode;

        bCode = bCode.contains(",")?bCode :bCode;
        mCode = mCode.contains(",")?mCode:mCode;
        sCode = sCode.contains(",")?sCode:sCode;


        category += bCode.equalsIgnoreCase("0")? "":"" + epicureRegionRepository.findByCode(Integer.parseInt(bCode)).get(0).getListName();
        category += mCode.equalsIgnoreCase("0")? "":" | " + epicureRegionRepository.findByCode(Integer.parseInt(mCode)).get(0).getListName();
        if(!sCode.equals("")){
            category += sCode.equalsIgnoreCase("0")? "":" | " + epicureRegionRepository.findByCode(Integer.parseInt(sCode)).get(0).getListName();
        }

        return category;
    }
    public String getMainShopCategory(String bCode, String mCode, String sCode){

        String category = "";
        bCode = bCode.startsWith(",")?bCode.substring(1):bCode;
        mCode = mCode.startsWith(",")?mCode.substring(1):mCode;
        sCode = sCode.startsWith(",")?sCode.substring(1):sCode;

        bCode = bCode.contains(",")?bCode.split(",")[0]:bCode;
        mCode = mCode.contains(",")?mCode.split(",")[0]:mCode;
        sCode = sCode.contains(",")?sCode.split(",")[0]:sCode;



        category += bCode.equalsIgnoreCase("0")? "":"" + epicureRegionRepository.findByCode(Integer.parseInt(bCode)).get(0).getListName();
        category += mCode.equalsIgnoreCase("0")? "":" | " + epicureRegionRepository.findByCode(Integer.parseInt(mCode)).get(0).getListName();
        category += sCode.equalsIgnoreCase("0")? "":" | " + epicureRegionRepository.findByCode(Integer.parseInt(sCode)).get(0).getListName();

        return category;
    }
    public String getListShopCategory(String bCode, String mCode, String address){

        String category = "";
        bCode = bCode.startsWith(",")?bCode:bCode;
        mCode = mCode.startsWith(",")?mCode:mCode;

        bCode = bCode.contains(",")?bCode :bCode;
        mCode = mCode.contains(",")?mCode:mCode;

        category += bCode.equalsIgnoreCase("0")? "":"" + epicureRegionRepository.findByCode(Integer.parseInt(bCode)).get(0).getListName();
        category += mCode.equalsIgnoreCase("0")? "":" | " + epicureRegionRepository.findByCode(Integer.parseInt(mCode)).get(0).getListName();

        return category;
    }
}
