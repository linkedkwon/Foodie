package kr.foodie.repo;

import kr.foodie.domain.category.Category;
import kr.foodie.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByRegionTypeAndProvinceNameContaining(String regionType, String provinceName);
    List<Category> findByRegionTypeAndDistrictName(String regionType, String districtName);
}
