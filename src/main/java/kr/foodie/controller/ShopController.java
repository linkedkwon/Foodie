package kr.foodie.controller;

import java.util.List;
import java.util.Optional;

import kr.foodie.entity.ShopEntity;
import kr.foodie.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {
  private final ShopRepository shopRepository;

  @GetMapping("/all")
  public List<ShopEntity> findAllShop() {
    return shopRepository.findAll();
  }

  @GetMapping("/{id}")
  public Optional<ShopEntity> findById(@PathVariable int id) {
    return shopRepository.findById(id);
  }

  @GetMapping("/regionId/{id}")
  public List<ShopEntity> findByRegionId(@PathVariable int id) {
    return shopRepository.findByRegionId(id);
  }

  @GetMapping("/subwayId/{id}")
  public List<ShopEntity> findBySubwayTypeId(@PathVariable int id) {
    return shopRepository.findBySubwayTypeId(id);
  }

  @GetMapping("/villageId/{id}")
  public List<ShopEntity> findByVillageTypeId(@PathVariable int id) {
    return shopRepository.findByVillageTypeId(id);
  }
}
