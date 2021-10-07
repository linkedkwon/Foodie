package kr.foodie.repository;

import kr.foodie.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Integer> {
  List<ShopEntity> findByRegionId(int regionId);
  List<ShopEntity> findByRegionIdAndShopType(int regionId, String shopType);
  List<ShopEntity> findBySubwayTypeId(int subwayTypeId);
  List<ShopEntity> findBySubwayTypeIdAndShopType(int subwayTypeId, String shopType);
  List<ShopEntity> findByVillageTypeId(int villageTypeId);
  List<ShopEntity> findByVillageTypeIdAndShopType(int villageTypeId, String shopType);

  @Query(value="SELECT * ,(6371*acos(cos(radians(?1))*cos(radians(slLat))*cos(radians(slLng)-radians(?2))+sin(radians(?1))*sin(radians(slLat)))) AS distance from shop where shop_type = ?3 having distance <= 20 order by distance", nativeQuery = true)
  List<ShopEntity> findWithPosition(String lat, String lng, String shopType);
}
