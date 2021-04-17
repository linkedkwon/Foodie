package kr.foodie.service;

import kr.foodie.domain.account.Review;
import kr.foodie.domain.account.ReviewDTO;
import kr.foodie.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

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

    public int getItemSizeByUserId(int userId){
        return reviewRepository.countByUserId(userId).orElseGet(()->{return 0;});
    }

    public int getItemSizeByShopId(int shopId){
        return reviewRepository.countByShopId(shopId).orElseGet(()->{return 0;});
    }

    public List<ReviewDTO> getItemsByUserId(int userId, int idx, String username){

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

    public List<ReviewDTO> getItemsByShopId(int shopId, int idx){

        String jpql = "select new kr.foodie.domain.account.ReviewDTO(" +
                "u.name, r.starRating, r.content) "
                +"from Review r right outer join User u "
                +"on r.userId = u.id "
                +"where r.shopId ="+ shopId
                +"order by r.createdTime desc";

        TypedQuery<ReviewDTO> query = em.createQuery(jpql, ReviewDTO.class)
                .setFirstResult(idx*itemInterval)
                .setMaxResults(itemInterval);

        List<ReviewDTO> reviews = query.getResultList();

        System.out.println("리뷰에여\n"+reviews);

        return reviews;
    }
}
