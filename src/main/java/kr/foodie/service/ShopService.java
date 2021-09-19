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
        return shopRepository.findByRegionIdAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(regionId, shopType,
                PageRequest.of(idx,interval,Sort.by("createdAt").descending())).getContent();
    }
    public List<Shop>  getSubwayShopInfos(Integer regionId, String shopType, int idx, int interval) {
        return shopRepository.findBySubwayTypeIdAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(regionId, shopType,
                PageRequest.of(idx,interval,Sort.by("createdAt").descending())).getContent();
    }

    public List<Shop> getSubwayPremiumShopInfos(Integer regionId, String shopType) {
        return shopRepository.findBySubwayTypeIdAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(regionId, shopType);
    }

    public List<Shop> getShopPremiumInfos(Integer regionId, String shopType) {
        return shopRepository.findByRegionIdAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(regionId, shopType);
    }


//    사이드에 그린리스트<-> 레드리스트 우선순위 지정필요
    public List<Shop> getShopInfosWithSideOrder(Integer regionId, String shopType) {
        if(shopType.equals("1")){
            return shopRepository.findTop4ByRegionIdAndShopType(regionId, "0");
        }else{
            return shopRepository.findTop4ByRegionIdAndShopType(regionId, "1");
        }
    }

    public List<Shop> getShopDetail(Integer shopId) {
        return shopRepository.findByShopId(shopId);
    }

    public List<Shop> getFilterShopList(String shopTypeId, Integer regionId, String filterItems) {
        return shopRepository.findByShopTypeAndRegionAndThemeList(shopTypeId, regionId, filterItems);
    }

    //검색 (with 가게이름)
    public List<Shop> getFilterShopListWithShopName(String shopTypeId, String filterItems) {
        return shopRepository.findByShopTypeAndShopName(shopTypeId, filterItems);
    }
    //검색 (with 주소)
    public List<Shop> getFilterShopListWithShopAddress(String shopTypeId, String filterItems) {
        return shopRepository.findByShopTypeAndAddress(shopTypeId, filterItems);
    }
    //검색 (with 내용)
    public List<Shop> getFilterShopListWithShopContent(String shopTypeId, String filterItems) {
        return shopRepository.findByShopTypeAndHistory(shopTypeId, filterItems);
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

    public List<Shop> getTop50AdminShopInfos(String shopType) {
        return shopRepository.findTop50ByShopType(shopType);
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
