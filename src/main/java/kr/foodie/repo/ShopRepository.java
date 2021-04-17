package kr.foodie.repo;

import kr.foodie.domain.shop.MainBoard;
import kr.foodie.domain.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Page<Shop> findByRegionTypeIdAndShopTypeAndOrderIsNull(String regionType, String shopType, Pageable pageable);

    List<Shop> findByRegionTypeIdAndShopTypeAndOrderIsLessThan(String regionType, String shopType, Integer num);
    List<Shop> findByRegionTypeIdAndShopTypeAndOrderIsGreaterThan(String regionType, String shopType, Integer num);
    List<Shop> findByShopId(Integer shopId);

    Optional<Integer> countByRegionTypeIdAndShopType(String regionType, String shopType);

    @Query(value="select * from shop where shop_id in (select shop_id from main_board where type = ?1)", nativeQuery = true)
    List<Shop> findShopInfoByType(Integer type);

    @Query(value="SELECT * ,(6371*acos(cos(radians(?1))*cos(radians(slLat))*cos(radians(slLng)-radians(?2))+sin(radians(?1))*sin(radians(slLat)))) AS distance from shop where shop_type = ?3 having distance <= 2 order by distance", nativeQuery = true)
    List<Shop> findByAddressContaining(String lat, String lng, String shopType);

}
