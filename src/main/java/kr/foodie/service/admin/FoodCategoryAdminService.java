package kr.foodie.service.admin;

import kr.foodie.domain.category.FoodCategory;
import kr.foodie.repo.admin.FoodCategoryAdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodCategoryAdminService {

    private final FoodCategoryAdminRepository regionRepository;


    public FoodCategoryAdminService(FoodCategoryAdminRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<FoodCategory> getAdminRegionBCategory() {
        return regionRepository.findByBcode();
    }
    public List<FoodCategory> getAdminRegionMCategory(Integer code) {
        return regionRepository.findByMcode(code);
    }
    public List<FoodCategory> getAdminRegionSCategory(Integer code)
    {
        return regionRepository.findByScode(code);
    }
}

