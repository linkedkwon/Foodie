package kr.foodie.service;

import kr.foodie.domain.account.Review;
import kr.foodie.domain.account.ReviewDTO;
import kr.foodie.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
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
                                .bestComment("0")
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
                "s.shopId, s.shopName, r.reviewId, r.content, r.starRating, r.bestComment) "
                +"from Shop s right outer join Review r "
                +"on s.shopId = r.shopId "
                +"where r.userId ="+ userId
                +"order by r.bestComment desc, r.createdTime desc";

        TypedQuery<ReviewDTO> query = em.createQuery(jpql, ReviewDTO.class)
                                        .setFirstResult(idx * itemInterval)
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
                "u.name, r.userId, r.reviewId, r.starRating, r.content, r.bestComment) "
                +"from Review r right outer join User u "
                +"on r.userId = u.id "
                +"where r.shopId ="+ shopId
                +"order by r.bestComment desc, r.createdTime desc";

        TypedQuery<ReviewDTO> query = em.createQuery(jpql, ReviewDTO.class)
                .setFirstResult(idx*itemInterval)
                .setMaxResults(itemInterval);

        return query.getResultList();
    }

    public List<ReviewDTO> getAllReviews(){

        String jpql;
        jpql = "select new kr.foodie.domain.account.ReviewDTO("
                +"r.shopId, s.shopName,r.userId,  u.name,  r.reviewId, r.starRating, r.content, r.bestComment, "
                +"function('date_format', r.createdTime, '%Y년 %m월 %d일 %H시%i분')) "
                +"from Review r left join User u "
                +"on r.userId = u.id "
                +"left join Shop s "
                +"on s.shopId = r.shopId";

        TypedQuery<ReviewDTO> query = em.createQuery(jpql, ReviewDTO.class);

        return query.getResultList();
    }

    @Transactional
    public String deleteItemByReviewId(int reviewId){
        reviewRepository.deleteByReviewId(reviewId);
        return "1";
    }

    @Transactional
    public String deleteAllItem(int userId){
        reviewRepository.deleteAllByUserId(userId);
        return "1";
    }

    public String switchIsBest(int reviewId){
        Review entity = reviewRepository.findById(reviewId)
                .orElseThrow(()->{ return new EntityNotFoundException("review Id not found");
                });

        String isBest = entity.getBestComment().equals("1")?"0":"1";
        entity.setBestComment(isBest);
        reviewRepository.save(entity);

        return "1";
    }

    public String getShopTasteRatingAVG(Integer shopId){

        String jpql = "select new kr.foodie.domain.account.ReviewDTO("
                +"r.starRating) "
                +"from Review r "
                +"where r.shopId =" + shopId;

        TypedQuery<ReviewDTO> query = em.createQuery(jpql, ReviewDTO.class);
        List<ReviewDTO> reviewDTO = Optional.ofNullable(query.getResultList())
                .orElseThrow(() -> {return new EntityNotFoundException("has not have review");});
        if(reviewDTO.size() == 0)
            return "0,0";

        int sum = 0;
        for(ReviewDTO obj:reviewDTO)
            sum += Integer.parseInt(obj.getStarRating());

        return Integer.toString(sum/reviewDTO.size())+","+Integer.toString(reviewDTO.size());
    }
}