package kr.foodie.repo;

import kr.foodie.domain.shop.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
//    List<Shop> findByArea1stAndArea2stAndArea3st(Integer area1st, Integer area2st, Integer area3st);
    List<Region> findByRegionType(String regionType);
}
