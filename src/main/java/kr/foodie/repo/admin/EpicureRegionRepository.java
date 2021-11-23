package kr.foodie.repo.admin;

import kr.foodie.domain.shop.EpicureRegion;
import kr.foodie.domain.shop.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpicureRegionRepository extends JpaRepository<EpicureRegion, Long> {
    // 시/도 정보 조회
    @Query(value="select * from epicure_region where parent_no = 0 and visiable = \"yes\" and code= ?1 order by seq", nativeQuery=true)
    List<EpicureRegion> findByParentNoAndVisiable(String type);

    // 군/구 정보 조회
    @Query(value="select * from epicure_region where parent_no = ?1 and visiable = \"yes\" and code= ?2 order by seq", nativeQuery=true)
    List<EpicureRegion> getEpicureDistrict(Integer parentNo, String type);
}