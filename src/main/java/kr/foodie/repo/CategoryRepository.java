package kr.foodie.repo;

import kr.foodie.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByRegionTypeAndProvinceNameContaining(String regionType, String provinceName);
    List<Category> findByRegionTypeAndProvinceNameContainingAndDistrictNameContainingAndDistrictCntGreaterThan(String regionType, String bigDistrictName, String middleDistrictName, Integer defaultCount);
}
