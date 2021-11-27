package kr.foodie.repo.admin;

import kr.foodie.domain.shopItem.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionAdminRepository extends JpaRepository<Region, Long> {
    // select province_name from region where region_type = 1 group by province_name
    @Query(value="select province_name from region where region_type = 1 group by province_name", nativeQuery = true)
    List<String> findRegionProvinceInfo();

//    List<Region> findByArea1stAndArea2stAndArea3st(Integer area1st, Integer area2st, Integer area3st);

    @Query(value="select region_id from region where province_name = (select province_name from region where area1st =?1 and area2st = ?2,  area3st = ?3) and region_type=1", nativeQuery = true)
    List<Integer> findGreenRegionInfo(Integer area1st, Integer area2st, Integer area3st);

    @Query(value="select region_id from region where province_name = (select province_name from region where subwat = ?1) and region_type=1", nativeQuery = true)
    List<Integer> findGreenRegionInfoWithSubwayId(Integer area1st, Integer area2st, Integer area3st);

    @Query(value="select * from region where region_type = 1 and province_name like %?1%", nativeQuery = true)
    List<Region> findRegionDistrictInfo(String provinceName);

    @Query(value="select * from region where region_type = 3 ", nativeQuery = true)
    List<Region> findRegionFoodRegionInfo();

    @Query(value="select province_name from region where region_type = 2 group by province_name", nativeQuery = true)
    List<String> findRegionProvinceInfoWithRegionType2();

    @Query(value="select district_name from region where region_type = 2 and province_name like %?1% group by district_name", nativeQuery = true)
    List<String> findRegionDistrictInfoWithRegionType2(String provinceName);

    @Query(value="select * from region where region_type = 2 and province_name like %?1% and district_name like %?2%", nativeQuery = true)
    List<Region> findRegionSubwaytInfoWithRegionType2(String provinceName, String districtName);

}
