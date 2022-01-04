package kr.foodie.repo;

import kr.foodie.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByNameAndPhoneNum(String name, String phoneNum);
    Optional<User> findByEmailAndPhoneNum(String email, String phoneNum);
    Optional<User> findByEmail(String email);

    Optional<Integer> countByEmail(String email);
    Optional<Integer> countByPhoneNum(String phonNum);

    List<User> findAllByUserType(String userType);

    void deleteById(int userId);

}

