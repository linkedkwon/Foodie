package kr.foodie.service;

import kr.foodie.domain.category.Category;
import kr.foodie.repo.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategory(String regionType, String regionName) {
        return categoryRepository.findByRegionTypeAndProvinceNameContaining(regionType, regionName);
    }
}
