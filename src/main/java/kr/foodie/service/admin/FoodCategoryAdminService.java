package kr.foodie.service.admin;

import kr.foodie.domain.category.FoodCategory;
import kr.foodie.repo.admin.FoodCategoryAdminRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class FoodCategoryAdminService {

    private final FoodCategoryAdminRepository foodCategoryAdminRepository;


    public FoodCategoryAdminService(FoodCategoryAdminRepository foodCategoryAdminRepository) {
        this.foodCategoryAdminRepository = foodCategoryAdminRepository;
    }

    public HashMap<String, String> getShopCategoryNameCode(String bCode, String mCode, String sCode){

        HashMap<String, String> category = new HashMap<String, String>();

        bCode = bCode.startsWith(",")?bCode.substring(1):bCode;
        mCode = mCode.startsWith(",")?mCode.substring(1):mCode;
        sCode = sCode.startsWith(",")?sCode.substring(1):sCode;

        bCode = bCode.contains(",")?bCode.split(",")[0]:bCode;
        mCode = mCode.contains(",")?mCode.split(",")[0]:mCode;

//        category += address.split(" ")[0]+ " " + address.split(" ")[1];
//        category += bCode.equalsIgnoreCase("0")? "":" | " + foodCategoryAdminRepository.findByBCodeAndLevel(Integer.parseInt(bCode),1).get(0).getCategoryName();

//        foodCategoryAdminRepository.findByBCodeAndLevel(Integer.parseInt(bCode),1).get(0).getBCode();
//        category.put(bCode.toString(),foodCategoryAdminRepository.findByBCodeAndLevel(Integer.parseInt(bCode),1).get(0).getCategoryName());
//        category.put(mCode.toString(),foodCategoryAdminRepository.findByMCodeAndLevel(Integer.parseInt(mCode),2).get(0).getCategoryName());
        if(bCode.equals("")){
            category.put("bCode", null);
        }else{
            category.put(bCode.toString(),foodCategoryAdminRepository.findByBCodeAndLevel(Integer.parseInt(bCode),1).get(0).getCategoryName());
        }
        if(mCode.equals("")){
            category.put("mCode", null);
        }else{
            category.put(mCode.toString(),foodCategoryAdminRepository.findByMCodeAndLevel(Integer.parseInt(mCode),2).get(0).getCategoryName());
        }
        if(sCode.equals("")){
            category.put("sCode", null);
        }else{
            category.put(sCode.toString(),foodCategoryAdminRepository.findBySCodeAndLevel(Integer.parseInt(sCode),3).get(0).getCategoryName());
        }
        return category;
    }

    public List<FoodCategory> getAdminRegionBCategory() {
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
    }
}

