package kr.foodie.service;

import kr.foodie.domain.category.Category;
import kr.foodie.domain.shop.Shop;
import kr.foodie.repo.CategoryRepository;
import kr.foodie.repo.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    private final ShopRepository shopRepository;


    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<Shop> getShopInfos(String regionTypeId) {
        return shopRepository.findByRegionTypeId(regionTypeId);
    }
}
