package kr.foodie.repo;

import kr.foodie.domain.shopItem.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<HashTag, Long> {
    List<HashTag> findByShopId(Integer shopId);
}
