package kr.foodie.repo;

import kr.foodie.domain.account.Review;
import kr.foodie.domain.account.ReviewDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Optional<Integer> countByUserId(int userId);
    Optional<Integer> countByShopId(int shopId);
    void deleteByReviewId(int reviewId);
    void deleteAllByUserId(int userId);

    @Query(value="select s.shop_id, s.shop_name, r.user_id, u.name, r.content, r.star_rating, r.created_at   from review r left join user u on r.user_id = u.user_id left join shop s  on s.shop_id = r.shop_id where r.user_id is not null;", nativeQuery = true)
    List<ReviewDTO> getAllComment();

}
