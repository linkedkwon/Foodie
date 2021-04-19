package kr.foodie.service;

import kr.foodie.domain.category.Category;
import kr.foodie.repo.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategory(String regionType, String regionName) {
        List<Category> categories = categoryRepository.findByRegionTypeAndProvinceNameContaining(regionType, regionName);
        categories.stream()
                .filter(o -> Optional.ofNullable(o.getDistrictCnt()).isEmpty())
                .forEach(o -> {
                    o.setDistrictCnt(0);
                });

        return categories;
    }
    public List<Category> getCategorySecondType(String regionType, String districtName) {
        return categoryRepository.findByRegionTypeAndDistrictName(regionType, districtName);
    }
}
