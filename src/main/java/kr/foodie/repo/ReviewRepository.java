package kr.foodie.repo;

import kr.foodie.domain.account.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Optional<Integer> countByUserId(int userId);
}
