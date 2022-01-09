package kr.foodie.controller;

import kr.foodie.VO.FavoriteShopVO;
import kr.foodie.entity.FavoriteShopEntity;
import kr.foodie.repository.FavoriteShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/favoriteShop")
public class FavoriteShopController {
  private final FavoriteShopRepository favoriteShopRepository;

  // 최신순으로 return
  @GetMapping("/user/{id}")
  public List<FavoriteShopEntity> findByUserId(@PathVariable int id) {
    return favoriteShopRepository.findByUserIdOrderByCreatedAtDesc(id);
  }

  @GetMapping("/shop/{id}")
  public List<FavoriteShopEntity> findByShopId(@PathVariable int id) {
    return favoriteShopRepository.findByShopId(id);
  }

  @PostMapping("")
  public String postFavorite(@RequestBody FavoriteShopVO favoriteShopVO) {
    favoriteShopRepository.save(FavoriteShopEntity.builder()
            .userId(favoriteShopVO.getUserId())
            .shopId(favoriteShopVO.getShopId())
            .createdAt(Calendar.getInstance().getTime()).build()
    );
    //todo: post if not exists
    return "success";
  }

  @DeleteMapping("/{id}")
  public String deleteFavorite(@PathVariable int id) {
    favoriteShopRepository.deleteById(id);
    return "success";
  }
}
