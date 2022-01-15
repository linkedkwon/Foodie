package kr.foodie.repository;

import kr.foodie.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
  UserEntity findByUserId(int userId);

  @Transactional
  @Modifying
  @Query(value="update user set name=:name, point=:point where user_id=:userId", nativeQuery = true)
  void updateUser(
          @Param("name") String name,
          @Param("point") int point,
          @Param("userId") int userId
  );
}
