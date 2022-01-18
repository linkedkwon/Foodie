package kr.foodie.service.admin;

import kr.foodie.domain.account.AdminInquiryListVO;
import kr.foodie.domain.account.AdminReviewListVO;
import kr.foodie.domain.account.Inquiry;
import kr.foodie.domain.account.InquiryDTO;
import kr.foodie.repo.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class AdminInquiryService {

    @PersistenceContext
    private EntityManager em;

    private final InquiryRepository inquiryRepository;

    private static final String select = "select new kr.foodie.domain.account.InquiryDTO("
            +"i.inquiryId, u.name, u.email, i.title, i.content, u.phoneNum, i.comment, i.givenReply, "
            +"function('date_format', i.createdTime, '%Y-%m-%d')) ";
    private static final String join = "from Inquiry i left join User u "
            +"on i.userId = u.id";

    public List<InquiryDTO> getAdminInquiryList(AdminInquiryListVO vo){
        String jpql =  select + join;

        TypedQuery<InquiryDTO> query = em.createQuery(addQueryByFilter(vo, jpql), InquiryDTO.class)
                .setFirstResult((vo.getPage() - 1) * 15)
                .setMaxResults(15);

        return query.getResultList();
    }

    public InquiryDTO getAdminInquiryById(int id){
        String jpql = select + join + " where i.inquiryId =" + id;

        return em.createQuery(jpql, InquiryDTO.class).getSingleResult();
    }

    public int getAllInquiryCount(AdminInquiryListVO vo){
        String jpql = "select count(i) " + join;
        Query query = em.createQuery(addQueryByFilter(vo, jpql));

        return Math.toIntExact((long) query.getSingleResult());
    }

    public List<Integer> getPages(AdminInquiryListVO vo, int size){
        List<Integer> pages = new ArrayList<>();
        int pageSize = size / 15;
        pageSize += size % 15 > 0 ? 1 : 0;

        int left = 1;
        if(vo.getPage() == 1) {
            left = 1;
        }
        else{
            int currentPage = vo.getPage();
            left = currentPage % 10 == 0 ?
                    (currentPage / 10 - 1) * 10 + 1 : (currentPage / 10) * 10 + 1;
        }

        for(int i = left; i <= pageSize; i++){
            if(i == left + 10)
                break;
            pages.add(i);
        }
        return pages;
    }

    public Map<String, Integer> getBtnPages(AdminInquiryListVO vo, int size){
        int pageSize = size / 15;
        pageSize += size % 15 > 0 ? 1 : 0;

        int page = vo.getPage();

        int nextFlag = page % 10 == 0 ? (page / 10 - 1) * 10 + 11 : (page / 10) * 10 + 11;
        int prevFlag = page % 10 == 0 ? (page / 10 - 2) * 10 + 1: (page / 10 - 1) * 10 + 1;

        Map<String, Integer> btnValues = new HashMap<>();
        btnValues.put("first", page - 11 >= 0 ? 1 : -1);
        btnValues.put("prev", page - 11 >= 0 ? prevFlag : -1);
        btnValues.put("last", nextFlag <= pageSize ? pageSize : -1);
        btnValues.put("next", nextFlag <= pageSize ? nextFlag : -1);

        return btnValues;
    }

    private String addQueryByFilter(AdminInquiryListVO vo, String jpql){
        boolean hasWhereCaluse = false;

        if(!vo.getKeyword().equals("-1")) {
            if (vo.getOption().equals("이름")) {
                jpql += " where u.name like '%" + vo.getKeyword() + "%'";
                hasWhereCaluse = true;
            }
            else if(vo.getOption().equals("제목")) {
                jpql += " where i.title like '%" + vo.getKeyword() + "%'";
                hasWhereCaluse = true;
            }
            else if(vo.getOption().equals("내용")) {
                jpql += " where i.content like '%" + vo.getKeyword() + "%'";
                hasWhereCaluse = true;
            }
        }

        if(!vo.getReplied().equals("-1")) {
            if(!hasWhereCaluse)
                jpql += " where i.givenReply like '%" + vo.getReplied() + "%'";
            else
                jpql += " and i.givenReply like '%" + vo.getReplied() + "%'";
        }

        jpql += " order by i.createdTime desc";

        return jpql;
    }

    @Transactional
    public String deleteUserById(List<Integer> list){
        for(int o : list){
            inquiryRepository.deleteByInquiryId(o);
        }
        return "1";
    }

    public void updateUserInfo(InquiryDTO dto){
        Optional<Inquiry> entity = inquiryRepository.findById(dto.getInquiryId());
        entity.get().setComment(dto.getComment());
        entity.get().setGivenReply(dto.getGivenReply());

        inquiryRepository.save(entity.get());
    }
}
