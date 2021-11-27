package kr.foodie.repo;

import kr.foodie.domain.shopItem.MainBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainBoardRepository extends JpaRepository<MainBoard, Long> {
    @Query(value="select * from main_board where type in :ids", nativeQuery = true)
    List<MainBoard> findByType(@Param("ids") List<String> types);
}
