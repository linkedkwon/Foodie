package kr.foodie.controller;

import kr.foodie.VO.ReviewVO;
import kr.foodie.entity.ReviewEntity;
import kr.foodie.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/review")
public class ReviewController {
  private final ReviewRepository reviewRepository;

  @GetMapping("/userId/{id}")
  public List<ReviewEntity> findByUserId(@PathVariable int id) {
    return reviewRepository.findByUserIdOrderByCreatedAtDesc(id);
  }

  @GetMapping("/shopId/{id}")
  public List<ReviewEntity> findByShopId(@PathVariable int id) {
    return reviewRepository.findByShopIdOrderByCreatedAtDesc(id);
  }

  @PostMapping("/shopId/{id}")
  public String createReview(@PathVariable int id, @RequestBody ReviewVO reviewVO) {
    reviewRepository.save(ReviewEntity.builder()
            .content(reviewVO.getContent())
            .createdAt(Calendar.getInstance().getTime())
            .shopId(id)
            .starRating(reviewVO.getStarRating())
            .userId(reviewVO.getUserId())
            .bestComment(reviewVO.getBestComment()).build()
    );
    return "success";
  }
}
