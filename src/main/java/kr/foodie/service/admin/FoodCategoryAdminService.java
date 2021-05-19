package kr.foodie.service.admin;

import kr.foodie.domain.category.FoodCategory;
import kr.foodie.domain.user.User;
import kr.foodie.repo.admin.FoodCategoryAdminRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodCategoryAdminService {

    private final FoodCategoryAdminRepository foodCategoryAdminRepository;


    public FoodCategoryAdminService(FoodCategoryAdminRepository foodCategoryAdminRepository) {
        this.foodCategoryAdminRepository = foodCategoryAdminRepository;
    }

    public List<FoodCategory> getAdminRegionBCategory() {
        return foodCategoryAdminRepository.findByBcodeOrderBySeq();
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

