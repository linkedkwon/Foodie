package kr.foodie.repo.admin;

import kr.foodie.domain.category.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FoodCategoryAdminRepository extends JpaRepository<FoodCategory, Long> {

    @Query(value="select * from food_category where level = 1 and type is null order by seq", nativeQuery = true)
    List<FoodCategory> findByBcodeOrderBySeq();

    @Query(value="select * from food_category where level = 1 and type = 1 order by seq", nativeQuery = true)
    List<FoodCategory> findByTripBcodeOrderBySeq();

    @Query(value="select * from food_category where bcode=?1 and level = 2", nativeQuery = true)
    List<FoodCategory> findByMcode(Integer code);

    @Query(value="select * from food_category where mcode=?1 and level = 3", nativeQuery = true)
    List<FoodCategory> findByScode(Integer code);

    @Query(value="select * from food_category where bcode=?1 and level = ?2", nativeQuery = true)
    List<FoodCategory> findByBCodeAndLevel(Integer bcode, Integer level);

    @Query(value="select * from food_category where mcode=?1 and level = ?2", nativeQuery = true)
    List<FoodCategory> findByMCodeAndLevel(Integer mcode, Integer level);

    @Query(value="select * from food_category where scode=?1 and level = ?2", nativeQuery = true)
    List<FoodCategory> findBySCodeAndLevel(Integer scode, Integer level);

    @Query(value="select * from food_category where level = ?1 and seq = ?2", nativeQuery = true)
    List<FoodCategory> findByLevelAndSeq(Integer level, Integer seq);

    @Transactional
    @Modifying
    @Query(value="update food_category set seq = ?1 where bcode=?2 and level = 1", nativeQuery = true)
    void updateBCodeSeq(Integer seq, Integer bcode);
}
