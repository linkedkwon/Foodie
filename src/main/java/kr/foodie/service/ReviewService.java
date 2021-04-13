package kr.foodie.service;

import kr.foodie.domain.account.Review;
import kr.foodie.domain.account.ReviewDTO;
import kr.foodie.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
public class ReviewService {

    private static final int itemInterval = 6;

    @Autowired
    private ReviewRepository reviewRepository;

    @PersistenceContext
    private EntityManager em;

    public String addItem(int userId, int shopId,
                            String starRating, String content){

        reviewRepository.save(Review.builder()
                                .userId(userId)
                                .shopId(shopId)
                                .starRating(starRating)
                                .content(content)
                                .createdTime(Calendar.getInstance().getTime())
                                .build());
        return "1";
    }

    public int getItemSize(int userId){
        return reviewRepository.countByUserId(userId).orElseGet(()->{return 0;});
    }

    public List<ReviewDTO> getItems(int userId, int idx, String username){

        String jpql = "select new kr.foodie.domain.account.ReviewDTO(" +
                "s.shopId, s.shopName, r.content, r.starRating) "
                +"from Shop s right outer join Review r "
                +"on s.shopId = r.shopId "
                +"where r.userId ="+ userId
                +"order by r.createdTime desc";

        TypedQuery<ReviewDTO> query = em.createQuery(jpql, ReviewDTO.class)
                                        .setFirstResult(idx*itemInterval)
                                        .setMaxResults(itemInterval);

        List<ReviewDTO> reviews = query.getResultList();

        reviews.stream().forEach(e -> {
            e.setUserName(username);
            e.setUrl("/shop/"+e.getShopId());
        });

        return reviews;
    }
}
