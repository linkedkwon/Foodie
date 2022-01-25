package kr.foodie.service;

import kr.foodie.domain.account.Inquiry;
import kr.foodie.repo.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private static final int itemInterval = 5;

    @PersistenceContext
    private EntityManager em;

    private final InquiryRepository inquiryRepository;

    public void createInquiry(int userId, String userName, String title, String content){
        inquiryRepository.save(Inquiry.builder()
                                .userId(userId)
                                .userName(userName)
                                .title(title)
                                .content(content)
                                .comment("")
                                .givenReply("0")
                                .createdTime(Calendar.getInstance().getTime())
                                .modifiedTime(Calendar.getInstance().getTime()).build());
    }

    public int getItemSizeByUserId(int userId){
        return inquiryRepository.countByUserId(userId).orElseGet(()->{return 0;});
    }

    public List<Inquiry> getInquiryList(int userId, int idx){

        String jpql = "select i "
                +"from Inquiry i "
                +"where i.userId = " + userId
                +"order by i.comment desc, i.modifiedTime desc";

        Query query = em.createQuery(jpql);
        query.setFirstResult(idx * itemInterval);
        query.setMaxResults(itemInterval);

        return query.getResultList();
    }

    public Inquiry getInquiryByInquiryId(long inquiryId){
        Inquiry inquiry = inquiryRepository.getInquiryByInquiryId(inquiryId);
        return inquiry;
    }

    @Transactional
    public String deleteItem(long inquiryId){
        inquiryRepository.deleteByInquiryId(inquiryId);
        return "1";
    }

    @Transactional
    public String deleteAllItem(int userId){
        inquiryRepository.deleteAllByUserId(userId);
        return "1";
    }
}
