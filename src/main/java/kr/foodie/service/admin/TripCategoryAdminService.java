package kr.foodie.service.admin;

import kr.foodie.domain.category.FoodCategory;
import kr.foodie.repo.admin.FoodCategoryAdminRepository;
import kr.foodie.repo.admin.TripCategoryAdminRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TripCategoryAdminService {

    private final TripCategoryAdminRepository tripCategoryAdminRepository;


    public TripCategoryAdminService(TripCategoryAdminRepository tripCategoryAdminRepository) {
        this.tripCategoryAdminRepository = tripCategoryAdminRepository;
    }
    public List<FoodCategory> getTripBcodeCategory() {
        return tripCategoryAdminRepository.findByBcodeOrderBySeq();
    }

    public List<FoodCategory> getTripMcodeCategory(Integer code) {
        return tripCategoryAdminRepository.findByMcode(code);
    }
    public List<FoodCategory> getTripScodeCategory(Integer code) {
        return tripCategoryAdminRepository.findByScode(code);
    }

}

