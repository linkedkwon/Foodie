package kr.foodie.repo;

import kr.foodie.domain.category.Theme;
import kr.foodie.domain.shop.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    List<Theme> findByType(Integer type);
}
