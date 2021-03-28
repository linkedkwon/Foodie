package kr.foodie.repo;

import kr.foodie.domain.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findByRegionTypeIdAndShopTypeAndOrderIsNull(String regionType, String shopType);
    List<Shop> findByRegionTypeIdAndShopTypeAndOrderIsLessThan(String regionType, String shopType, Integer num);
    List<Shop> findByRegionTypeIdAndShopTypeAndOrderIsGreaterThan(String regionType, String shopType, Integer num);
    List<Shop> findByShopId(Integer shopId);
}
