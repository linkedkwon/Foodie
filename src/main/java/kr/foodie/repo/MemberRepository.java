package kr.foodie.repo;

import kr.foodie.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNameAndPhoneNum(String name, String phoneNum);
    Optional<Member> findByEmailAndPhoneNum(String email, String phoneNum);
}
