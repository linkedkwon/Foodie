package kr.foodie.service;

import kr.foodie.domain.category.Category;
import kr.foodie.repo.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategory(String regionType, String regionName) {
        return initDistrictCnt(categoryRepository.findByRegionTypeAndProvinceNameContaining(regionType, regionName));
    }

    public List<Category> getCategorySecondType(String regionType, String districtName) {
        return initDistrictCnt(categoryRepository.findByRegionTypeAndDistrictName(regionType, districtName));
    }

    private List<Category> initDistrictCnt(List<Category> categories){

        categories.stream()
                .filter(o -> Optional.ofNullable(o.getDistrictCnt()).isEmpty())
                .forEach(o -> {
                    o.setDistrictCnt(0);
                });

        return categories;
    }
}
