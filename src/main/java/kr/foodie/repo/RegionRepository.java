package kr.foodie.repo;

import kr.foodie.domain.shopItem.EpicureRegion;
import kr.foodie.domain.shopItem.Region;
import kr.foodie.domain.shopItem.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
}
