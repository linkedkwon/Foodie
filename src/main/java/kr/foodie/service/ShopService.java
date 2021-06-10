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

    public List<Shop> getShopInfos(String regionTypeId, String shopType, int idx, int interval) {
        return shopRepository.findByRegionTypeIdAndShopTypeAndOrderIsNull(regionTypeId, shopType,
                PageRequest.of(idx,interval,Sort.by("createdAt").descending())).getContent();
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

    public List<Shop> getShopInfoByType(Integer type) {
        return shopRepository.findShopInfoByType(type);
    }

    public List<Shop> getShopInfoByAddress(String lat, String lng, String shopType) {
        return shopRepository.findByAddressContaining(lat, lng, shopType);
    }

    public int getItemSizeByRegionTypeAndShopType(String regionType, String shopType){
        return shopRepository.countByRegionTypeIdAndShopType(regionType, shopType).orElseGet(()->{return 0;});
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
        return shopRepository.findByShopTypeAndRegionTypeId(shopType, regionId);
    }

    public List<Shop> getAdminShopInfosWithBcodeWithRegionId(Integer bCode, String shopType, Integer regionId) {
        return shopRepository.findByBigCategoryAndShopTypeAndRegionTypeId(bCode, shopType, regionId);
    }
    public List<Shop> getAdminShopInfosWithBcodeAndMcodeWithRegionId(Integer bCode, Integer mCode, String shopType, Integer regionId) {
        return shopRepository.findByBigCategoryAndMiddleCategoryAndShopTypeAndRegionTypeId(bCode, mCode, shopType, regionId);
    }
    public List<Shop> getAdminShopInfosWithBcodeAndMcodeAndScodeWithRegionId(Integer bCode, Integer mCode, Integer sCode, String shopType, Integer regionId) {
        return shopRepository.findByBigCategoryAndMiddleCategoryAndSmallCategoryAndShopTypeAndRegionTypeId(bCode, mCode, sCode, shopType, regionId);
    }

}
