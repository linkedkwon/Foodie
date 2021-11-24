package kr.foodie.service.admin;

import kr.foodie.domain.category.FoodCategory;
import kr.foodie.repo.admin.EpicureRegionRepository;
import kr.foodie.repo.admin.FoodCategoryAdminRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;



@Service
public class FoodCategoryAdminService {

    private final EpicureRegionRepository epicureRegionRepository;

    static boolean isStringEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public FoodCategoryAdminService(EpicureRegionRepository epicureRegionRepository) {
        this.epicureRegionRepository = epicureRegionRepository;
    }

    public HashMap<String, String> getShopCategoryNameCode(String bCode, String mCode, String sCode, String type){

        HashMap<String, String> category = new HashMap<String, String>();

        bCode = bCode.startsWith(",")?bCode.substring(1):bCode;
        mCode = mCode.startsWith(",")?mCode.substring(1):mCode;
        sCode = sCode.startsWith(",")?sCode.substring(1):sCode;

        bCode = bCode.contains(",")?bCode.split(",")[0]:bCode;
        mCode = mCode.contains(",")?mCode.split(",")[0]:mCode;

        if(isStringEmpty(bCode)){
            category.put("bCode", null);
        }else{
            category.put(bCode.toString(),epicureRegionRepository.getRegionFirstInfoByRegionId(Integer.parseInt(bCode),type).get(0).getListName());
        }
        if(isStringEmpty(mCode)){
            category.put("mCode", null);
        }else{
            category.put(mCode.toString(),epicureRegionRepository.getRegionSecondInfoByRegionId(Integer.parseInt(mCode),type).get(0).getListName());
        }
        if(isStringEmpty(sCode)){
            category.put("sCode", null);
        }else{
            category.put(sCode.toString(),epicureRegionRepository.getRegionSecondInfoByRegionId(Integer.parseInt(sCode),type).get(0).getListName());
        }
        return category;
    }

    /*public List<FoodCategory> getAdminRegionBCategory() {
        return foodCategoryAdminRepository.findByBcodeOrderBySeq();
    }

    public List<FoodCategory> getAdminTripRegionBCategory() {
        return foodCategoryAdminRepository.findByTripBcodeOrderBySeq();
    }

    public List<FoodCategory> getAdminRegionMCategory(Integer code) {
        return foodCategoryAdminRepository.findByMcode(code);
    }
    public List<FoodCategory> getAdminRegionSCategory(Integer code)
    {
        return foodCategoryAdminRepository.findByScode(code);
    }*/
}

