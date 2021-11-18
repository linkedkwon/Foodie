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

        foodCategoryAdminRepository.findByBCodeAndLevel(Integer.parseInt(bCode),1).get(0).getBCode();
        category.put(bCode.toString(),foodCategoryAdminRepository.findByBCodeAndLevel(Integer.parseInt(bCode),1).get(0).getCategoryName());
        category.put(mCode.toString(),foodCategoryAdminRepository.findByMCodeAndLevel(Integer.parseInt(mCode),2).get(0).getCategoryName());
        category.put(sCode.toString(),foodCategoryAdminRepository.findBySCodeAndLevel(Integer.parseInt(sCode),3).get(0).getCategoryName());


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

    public List<FoodCategory> updateSeqPlusOne(Integer level, Integer id)
    {
//        List<FoodCategory> pre_entity = foodCategoryAdminRepository.findByBCodeAndLevel(id-1, level);
        List<FoodCategory> current_entity = foodCategoryAdminRepository.findByBCodeAndLevel(id, level);
        int currentSeq = current_entity.get(0).getSeq();
        int currentBCode = current_entity.get(0).getBCode();

        if(currentSeq == 1){
            return null;
        }
        List<FoodCategory> pre_entity = foodCategoryAdminRepository.findByLevelAndSeq(level,currentSeq-1);
        int preSeq = pre_entity.get(0).getSeq();
        int preBCode = pre_entity.get(0).getBCode();

        foodCategoryAdminRepository.updateBCodeSeq(preSeq, currentBCode);
        foodCategoryAdminRepository.updateBCodeSeq(currentSeq, preBCode);
        return foodCategoryAdminRepository.findByBcodeOrderBySeq();
    }

    public List<FoodCategory> updateSeqMinusOne(Integer level, Integer id)
    {
        List<FoodCategory> current_entity = null;
        if(level == 1){
            current_entity = foodCategoryAdminRepository.findByBCodeAndLevel(id, level);
        }else if(level == 2){
            current_entity = foodCategoryAdminRepository.findByMCodeAndLevel(id, level);
        }else if(level == 3){
            current_entity = foodCategoryAdminRepository.findBySCodeAndLevel(id, level);
        }
        int currentSeq = current_entity.get(0).getSeq();
        int currentBCode = current_entity.get(0).getBCode();

        if(currentSeq == 1){
            return null;
        }

        List<FoodCategory> next_entity = null;
        if(level == 1){
            next_entity = foodCategoryAdminRepository.findByLevelAndSeq(level,currentSeq+1);
        }else if(level == 2){
            next_entity = foodCategoryAdminRepository.findByLevelAndSeq(level,currentSeq+1);
        }else if(level == 3){
            next_entity = foodCategoryAdminRepository.findByLevelAndSeq(level,currentSeq+1);
        }
        int nextSeq = next_entity.get(0).getSeq();
        int nextBCode = next_entity.get(0).getBCode();

        foodCategoryAdminRepository.updateBCodeSeq(nextSeq, currentBCode);
        foodCategoryAdminRepository.updateBCodeSeq(currentSeq, nextBCode);
        return foodCategoryAdminRepository.findByBcodeOrderBySeq();
    }
}

