package kr.foodie.repository;

import kr.foodie.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
  List<ReviewEntity> findByUserId(int userId);
  List<ReviewEntity> findByShopId(int shopId);
}
