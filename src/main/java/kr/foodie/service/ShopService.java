package kr.foodie.service;

import kr.foodie.domain.shop.Shop;
import kr.foodie.repo.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {

    @PersistenceContext
    private EntityManager em;

    private final FoodCategoryService foodCategoryService;
    private final ShopRepository shopRepository;

    public void initializeHibernateSearch() {
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Shop> getShopInfos(Integer regionId, String shopType, int idx, int interval) {
        List<Shop> shops = shopRepository.findByRegionIdAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(regionId, shopType,
                PageRequest.of(idx,interval,Sort.by("createdAt").descending())).getContent();

        return addAliasOnShops(shops);
    }
    public List<Shop>  getSubwayShopInfos(Integer regionId, String shopType, int idx, int interval) {
        return shopRepository.findBySubwayTypeIdAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(regionId, shopType,
                PageRequest.of(idx,interval,Sort.by("createdAt").descending())).getContent();
    }

    public List<Shop> getSubwayPremiumShopInfos(Integer regionId, String shopType) {
        return shopRepository.findBySubwayTypeIdAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(regionId, shopType);
    }

    public List<Shop> getShopPremiumInfos(Integer regionId, String shopType) {
        List<Shop> shops = shopRepository.findByRegionIdAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(regionId, shopType);
        return addAliasOnShops(shops);
    }


//    사이드에 그린리스트<-> 레드리스트 우선순위 지정필요
    public List<Shop> getShopInfosWithSideOrder(Integer regionId, String shopType) {
        if(shopType.equals("1")){
            List<Shop> shops = shopRepository.findTop4ByRegionIdAndShopType(regionId, "0");
            return addAliasOnShops(shops);
        }else{
            List<Shop> shops = shopRepository.findTop4ByRegionIdAndShopType(regionId, "1");
            return addAliasOnShops(shops);
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
        List<Shop> shops = shopRepository.findShopInfoByType(type);
        return addAliasOnShops(shops);
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

    protected List<Shop> addAliasOnShops(List<Shop> shops){
        for (Shop shop : shops) {
            String bCode = Optional.ofNullable(shop.getBigCategory()).orElseGet(() -> { return "0"; });
            String mCode = Optional.ofNullable(shop.getMiddleCategory()).orElseGet(() -> { return "0"; });
            shop.setShopAlias(foodCategoryService.getShopCategory(bCode, mCode, shop.getAddress()));
        }
        return shops;
    }

    public void searchKeyword(String keyword){

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        QueryBuilder qBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Shop.class)
                .get();

        Query luceneQuery = qBuilder
                .keyword()
                .wildcard()
                .onFields("shopName","menu","shopAlias","shopCharge","address","roadAddress","recommandMenu")
                .matching("*"+keyword+"*")
                .createQuery();

        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Shop.class);

        List<Shop> shops = fullTextQuery.getResultList();
    }
}
