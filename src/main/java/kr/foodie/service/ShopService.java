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

    public List<Shop> getShopInfos(String regionTypeId, String shopType) {
        return shopRepository.findByRegionTypeIdAndShopTypeAndOrderIsNull(regionTypeId, shopType);
    }
    public List<Shop> getShopInfosWithOrder(String regionTypeId, String shopType, Integer num) {
        return shopRepository.findByRegionTypeIdAndShopTypeAndOrderIsLessThan(regionTypeId, shopType, num);
    }
    public List<Shop> getShopInfosWithSideOrder(String regionTypeId, String shopType, Integer num) {
        return shopRepository.findByRegionTypeIdAndShopTypeAndOrderIsGreaterThan(regionTypeId, shopType, num);
    }
    public List<Shop> getShopDetail(Integer shopId) {
        return shopRepository.findByShopId(shopId);
    }
}
