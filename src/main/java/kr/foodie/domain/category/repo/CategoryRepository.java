package kr.foodie.domain.category.repo;

import kr.foodie.domain.category.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Region, Long> {
}
