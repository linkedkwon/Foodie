package kr.foodie.repo.admin;

import kr.foodie.domain.shop.Region;
import kr.foodie.domain.shop.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionAdminRepository extends JpaRepository<Region, Long> {
    // select province_name from region where region_type = 1 group by province_name
    @Query(value="select province_name from region where region_type = 1 group by province_name", nativeQuery = true)
    List<String> findRegionProvinceInfo();

    @Query(value="select * from region where region_type = 1 and province_name like %?1%", nativeQuery = true)
    List<Region> findRegionDistrictInfo(String provinceName);

    @Query(value="select * from region where region_type = 3 ", nativeQuery = true)
    List<Region> findRegionFoodRegionInfo();
}
