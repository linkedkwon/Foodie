package kr.foodie.repo;

import kr.foodie.domain.account.FavoriteShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteShopRepository extends JpaRepository<FavoriteShop, Integer> {

    Optional<Integer> countByUserId(int userId);
    Optional<FavoriteShop> findByUserIdAndShopId(int userId, int shopId);
    void deleteByUserIdAndShopId(int userId, int shopId);
    void deleteAllByUserId(int userId);

}
