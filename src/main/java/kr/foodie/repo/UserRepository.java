package kr.foodie.repo;

import kr.foodie.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNameAndPhoneNum(String name, String phoneNum);
    Optional<User> findByEmailAndPhoneNum(String email, String phoneNum);
}
