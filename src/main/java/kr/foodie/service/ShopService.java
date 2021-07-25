package kr.foodie.service;

import kr.foodie.domain.shop.Shop;
import kr.foodie.repo.ShopRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<Shop> getShopInfos(Integer regionId, String shopType, int idx, int interval) {
        return shopRepository.findByRegionIdAndShopTypeAndOrderIsNull(regionId, shopType,
                PageRequest.of(idx,interval,Sort.by("createdAt").descending())).getContent();
    }

    public List<Shop> getShopInfosWithOrder(Integer regionId, String shopType, Integer num) {
        return shopRepository.findByRegionIdAndShopTypeAndOrderIsLessThan(regionId, shopType, num);
    }

    public List<Shop> getShopInfosWithSideOrder(Integer regionId, String shopType, Integer num) {
        return shopRepository.findByRegionIdAndShopTypeAndOrderIsGreaterThan(regionId, shopType, num);
    }

    public List<Shop> getShopDetail(Integer shopId) {
        return shopRepository.findByShopId(shopId);
    }

    public List<Shop> getShopInfoByType(Integer type) {
        return shopRepository.findShopInfoByType(type);
    }

    public List<Shop> getShopInfoByAddressName(String address, Integer type) {
        return shopRepository.getShopInfoByAddressName(address, type);
    }

    public List<Shop> insertShopInfo(Integer shopId, Integer type){
        shopRepository.insertMainRecommand(shopId, type);
        return shopRepository.findShopInfoByType(type);
    }
    public List<Shop> deleteShopInfo(Integer shopId, Integer type){
        shopRepository.deleteMainRecommand(shopId, type);
        return shopRepository.findShopInfoByType(type);
    }

    public List<Shop> getShopInfoByAddress(String lat, String lng, String shopType) {
        return shopRepository.findByAddressContaining(lat, lng, shopType);
    }

    public int getItemSizeByRegionTypeAndShopType(Integer regionId, String shopType){
        return shopRepository.countByRegionIdAndShopType(regionId, shopType).orElseGet(()->{return 0;});
    }

    public List<Shop> getAdminShopInfos(String shopType) {
        return shopRepository.findByShopType(shopType);
    }

    public List<Shop> getAdminShopInfosWithBcode(Integer bCode, String shopType) {
        return shopRepository.findByBigCategoryAndShopType(bCode, shopType);
    }
    public List<Shop> getAdminShopInfosWithBcodeAndMcode(Integer bCode, Integer mCode, String shopType) {
        return shopRepository.findByBigCategoryAndMiddleCategoryAndShopType(bCode, mCode, shopType);
    }
    public List<Shop> getAdminShopInfosWithBcodeAndMcodeAndScode(Integer bCode, Integer mCode, Integer sCode, String shopType) {
        return shopRepository.findByBigCategoryAndMiddleCategoryAndSmallCategoryAndShopType(bCode, mCode, sCode, shopType);
    }


    public List<Shop> getAdminShopInfosWithRegionId(String shopType, Integer regionId) {
        return shopRepository.findByShopTypeAndRegionId(shopType, regionId);
    }

    public List<Shop> getAdminShopInfosWithBcodeWithRegionId(Integer bCode, String shopType, Integer regionId) {
        return shopRepository.findByBigCategoryAndShopTypeAndRegionId(bCode, shopType, regionId);
    }
    public List<Shop> getAdminShopInfosWithBcodeAndMcodeWithRegionId(Integer bCode, Integer mCode, String shopType, Integer regionId) {
        return shopRepository.findByBigCategoryAndMiddleCategoryAndShopTypeAndRegionId(bCode, mCode, shopType, regionId);
    }
    public List<Shop> getAdminShopInfosWithBcodeAndMcodeAndScodeWithRegionId(Integer bCode, Integer mCode, Integer sCode, String shopType, Integer regionId) {
        return shopRepository.findByBigCategoryAndMiddleCategoryAndSmallCategoryAndShopTypeAndRegionId(bCode, mCode, sCode, shopType, regionId);
    }

}
