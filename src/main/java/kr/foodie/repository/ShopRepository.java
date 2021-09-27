package kr.foodie.repository;

import kr.foodie.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Integer> {
  List<ShopEntity> findByRegionId(int regionId);
  List<ShopEntity> findBySubwayTypeId(int subwayTypeId);
  List<ShopEntity> findByVillageTypeId(int villageTypeId);
}
