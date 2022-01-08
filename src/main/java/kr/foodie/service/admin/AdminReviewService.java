package kr.foodie.service.admin;

import kr.foodie.domain.account.AdminReviewListVO;
import kr.foodie.domain.account.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminReviewService {

    @PersistenceContext
    private EntityManager em;

    public List<Review> getAdminReviewList(AdminReviewListVO vo){
        String jpql = "select r from Review r ";
        TypedQuery<Review> query = em.createQuery(jpql, Review.class)
                .setFirstResult((vo.getPage() - 1) * 15)
                .setMaxResults(15);

        return query.getResultList();
    }

    public int getAllReviewCount(AdminReviewListVO vo){
        String jpql = "select count(r) from Review r ";
        Query query = em.createQuery(addQueryByFilter(vo, jpql));

        return Math.toIntExact((long) query.getSingleResult());
    }

    private String addQueryByFilter(AdminReviewListVO vo, String jpql){
        if(!vo.getKeyword().equals("-1")) {
            if (vo.getOption().equals("이름"))
                jpql += "and r.email like '%" + vo.getKeyword() + "%'";
            else if(vo.getOption().equals("제목"))
                jpql += "and s.name like '%" + vo.getKeyword() + "%'";
            else if(vo.getOption().equals("내용"))
                jpql += "and s.name like '%" + vo.getKeyword() + "%'";
        }

        jpql += "order by s.createdDate desc";

        return jpql;
    }

}
