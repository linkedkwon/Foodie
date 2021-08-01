package kr.foodie.repo;

import kr.foodie.domain.account.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    Optional<Integer> countByUserId(int userId);
    void deleteByInquiryId(long inquiryId);
    void deleteAllByUserId(int userId);
}
