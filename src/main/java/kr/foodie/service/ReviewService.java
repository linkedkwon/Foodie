package kr.foodie.service;

import kr.foodie.domain.account.Review;
import kr.foodie.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public String addReview(int userId, String userName, int shopId,
                            String starRating, String content){

        reviewRepository.save(Review.builder()
                                .userId(userId)
                                .shopId(shopId)
                                .userName(userName)
                                .starRating(starRating)
                                .content(content)
                                .createdTime(Calendar.getInstance().getTime())
                                .build());
        return "1";
    }

}
