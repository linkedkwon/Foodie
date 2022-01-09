package kr.foodie.repository;

import kr.foodie.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
  UserEntity findByUserId(int userId);
}
