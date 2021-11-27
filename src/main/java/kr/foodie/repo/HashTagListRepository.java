package kr.foodie.repo;

import kr.foodie.domain.shopItem.HashTagList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashTagListRepository extends JpaRepository<HashTagList, Long> {
    @Query(value="select * from hash_tag_list where type=?1", nativeQuery = true)
    List<HashTagList> findAllList(String type);
}
