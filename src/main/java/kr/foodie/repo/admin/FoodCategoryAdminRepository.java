package kr.foodie.repo.admin;

import kr.foodie.domain.category.FoodCategory;
import kr.foodie.domain.shop.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodCategoryAdminRepository extends JpaRepository<FoodCategory, Long> {

    @Query(value="select * from food_category where level = 1", nativeQuery = true)
    List<FoodCategory> findByBcode();

    @Query(value="select * from food_category where mcode=?1 and level = 2)", nativeQuery = true)
    List<FoodCategory> findByMcode(Integer code);

    @Query(value="select * from food_category where scode=?1 and level = 3)", nativeQuery = true)
    List<FoodCategory> findByScode(Integer code);
}
