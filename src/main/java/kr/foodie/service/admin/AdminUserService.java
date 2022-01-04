package kr.foodie.service.admin;

import kr.foodie.domain.user.AdminUserListVO;
import kr.foodie.domain.user.User;
import kr.foodie.repo.InquiryRepository;
import kr.foodie.repo.ReviewRepository;
import kr.foodie.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AdminUserService {

    @PersistenceContext
    private EntityManager em;

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final InquiryRepository inquiryRepository;

    public List<User> getAdminUserList(AdminUserListVO vo){
        String jpql = "select s from User s ";
        TypedQuery<User> query = em.createQuery(addQueryByFilter(vo, jpql), User.class)
                .setFirstResult((vo.getPage() - 1) * 15)
                .setMaxResults(15);

        return query.getResultList();
    }

    public List<Integer> getPages(AdminUserListVO vo, int size){

        List<Integer> pages = new ArrayList<>();
        int pageSize = size / 15;
        pageSize += size % 15 > 0 ? 1 : 0;

        int left = 1;
        if(vo.getRenderOption().equals("1") || vo.getPage() == 1) {
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

    public Map<String, Integer> getBtnPages(AdminUserListVO vo, int size){
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

    public int getAllUserCount(AdminUserListVO vo){
        String jpql = "select count(s) from User s ";
        Query query = em.createQuery(addQueryByFilter(vo, jpql));

        return Math.toIntExact((long) query.getSingleResult());
    }

    private String addQueryByFilter(AdminUserListVO vo, String jpql){
        jpql += "where s.userType like '%" + vo.getUserType() + "%'";
        if(!vo.getKeyword().equals("-1")) {
            if (vo.getOption().equals("이메일"))
                jpql += "and s.email like '%" + vo.getKeyword() + "%'";
            else if(vo.getOption().equals("이름"))
                jpql += "and s.name like '%" + vo.getKeyword() + "%'";
        }
        jpql += "order by s.createdDate desc";

        return jpql;
    }

    @Transactional
    public String deleteUserById(List<Integer> list){
        for(int o : list){
            userRepository.deleteById(o);
            reviewRepository.deleteAllByUserId(o);
            inquiryRepository.deleteAllByUserId(o);
        }

        return "1";
    }
}
