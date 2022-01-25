package kr.foodie.repo;

import kr.foodie.domain.shopItem.ShopTown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopTownRepository extends JpaRepository<ShopTown, Long> {
    // 시/도 정보 조회
    @Query(value="select * from rankup_shop_town where parent_no = 0 and visiable = \"yes\" and code= ?1 order by seq", nativeQuery=true)
    List<ShopTown> findByParentNoAndVisiable(String type);
}
