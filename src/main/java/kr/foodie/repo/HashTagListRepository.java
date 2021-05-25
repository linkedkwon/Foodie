package kr.foodie.repo;

import kr.foodie.domain.category.FoodCategory;
import kr.foodie.domain.shop.HashTag;
import kr.foodie.domain.shop.HashTagList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashTagListRepository extends JpaRepository<HashTagList, Long> {
    @Query(value="select * from hash_tag_list where type=?1", nativeQuery = true)
    List<HashTagList> findAllList(String type);
}
