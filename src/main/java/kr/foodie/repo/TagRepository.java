package kr.foodie.repo;

import kr.foodie.domain.shop.HashTag;
import kr.foodie.domain.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<HashTag, Long> {
    List<HashTag> findByShopId(Integer shopId);
}