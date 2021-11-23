package kr.foodie.service;

import kr.foodie.domain.shop.AdminListShop;
import kr.foodie.domain.shop.Shop;
import kr.foodie.domain.shop.ShopDTO;
import kr.foodie.repo.ShopRepository;
import kr.foodie.repo.admin.RegionAdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopService {

    @PersistenceContext
    private EntityManager em;

    private final FoodCategoryService foodCategoryService;
    private final ShopRepository shopRepository;
    private final TripCategoryService tripCategoryService;
    private final RegionAdminRepository regionAdminRepository;

    public void initializeHibernateSearch() {
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Shop> getSubwayShopInfos(Integer regionId, String shopType, int idx, int interval) {
        if (shopType.equals("1")) {
            List<Integer> greenListRegionInfos = regionAdminRepository.findGreenRegionInfo(regionId);
            List<Shop> shops = shopRepository.findBySubwayTypeIdInAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(greenListRegionInfos, shopType,
                    PageRequest.of(idx, interval, Sort.by("createdAt").descending())).getContent();
            return addAliasOnShops(shops);
        } else {
            List<Shop> shops = shopRepository.findBySubwayTypeIdAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(regionId, shopType,
                    PageRequest.of(idx, interval, Sort.by("createdAt").descending())).getContent();
            return addAliasOnShops(shops);
        }
    }

    public List<Shop> getShopInfos(Integer regionId, String shopType, int idx, int interval) {
        if (shopType.equals("1")) {
            List<Integer> greenListRegionInfos = regionAdminRepository.findGreenRegionInfo(regionId);
            List<Shop> shops = shopRepository.findByRegionIdInAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(greenListRegionInfos, shopType,
                    PageRequest.of(idx, interval, Sort.by("createdAt").descending())).getContent();
            return addAliasOnShops(shops);
        } else {
            List<Shop> shops = shopRepository.findByRegionIdAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(regionId, shopType,
                    PageRequest.of(idx, interval, Sort.by("createdAt").descending())).getContent();
            return addAliasOnShops(shops);
        }
    }

    public List<Shop> getSubwayPremiumShopInfos(Integer regionId, String shopType) {
        if (shopType.equals("1")) {
            List<Integer> greenListRegionInfos = regionAdminRepository.findGreenRegionInfo(regionId);
            List<Shop> shops = shopRepository.findBySubwayTypeIdInAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(greenListRegionInfos, shopType);
            return addAliasOnShops(shops);
        } else {
            List<Shop> shops = shopRepository.findBySubwayTypeIdAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(regionId, shopType);
            return addAliasOnShops(shops);
        }
    }

    public List<Shop> getShopPremiumInfos(Integer regionId, String shopType) {
        if (shopType.equals("1")) {
            List<Integer> greenListRegionInfos = regionAdminRepository.findGreenRegionInfo(regionId);
            List<Shop> shops = shopRepository.findByRegionIdInAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(greenListRegionInfos, shopType);
            return addAliasOnShops(shops);
        } else {
            List<Shop> shops = shopRepository.findByRegionIdAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(regionId, shopType);
            return addAliasOnShops(shops);
        }
    }

    public List<Shop> getDuplicatedInfos(String shopType, String shopName) {
        if (shopType.equals("1")) {
            List<Shop> greenListDuplicatedInfos = shopRepository.findDuplicatedShop(shopType, shopName);
            return greenListDuplicatedInfos;
        } else {
            List<Shop> redListDuplicatedInfos = shopRepository.findDuplicatedShop(shopType, shopName);
            return redListDuplicatedInfos;
        }
    }

    //    사이드에 그린리스트<-> 레드리스트 우선순위 지정필요
    public List<Shop> getShopInfosWithSideOrder(Integer regionId, String shopType) {
        if (shopType.equals("1")) {
            List<Shop> shops = shopRepository.findTop4ByRegionIdAndShopType(regionId, "0");
            return addAliasOnShops(shops);
        } else {
            List<Shop> shops = shopRepository.findTop4ByRegionIdAndShopType(regionId, "1");
            return addAliasOnShops(shops);
        }
    }

    public Shop getShopDetail(Integer shopId) {
        return shopRepository.findById(shopId).orElseThrow();
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

    public List<Shop> insertShopInfo(Integer shopId, Integer type) {
        shopRepository.insertMainRecommand(shopId, type);
        return shopRepository.findShopInfoByType(type);
    }

    public List<Shop> deleteShopInfo(Integer shopId, Integer type) {
        shopRepository.deleteMainRecommand(shopId, type);
        return shopRepository.findShopInfoByType(type);
    }

    public List<Shop> getShopInfoByAddress(String lat, String lng, String shopType) {
        return shopRepository.findByAddressContaining(lat, lng, shopType);
    }

    public int getItemSizeByRegionTypeAndShopType(Integer regionId, String shopType) {
        return shopRepository.countByRegionIdAndShopType(regionId, shopType).orElseGet(() -> {
            return 0;
        });
    }

    public List<Shop> getAdminShopInfos(String shopType) {
        return shopRepository.findByShopType(shopType);
    }

    public List<Shop> getTop50AdminShopInfos(String shopType) {
        return shopRepository.findTop1000ByShopTypeOrderByUpdatedAtDesc(shopType);
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

    protected List<Shop> addAliasOnShops(List<Shop> shops) {
        for (Shop shop : shops) {
            String bCode = Optional.ofNullable(shop.getBigCategory()).orElseGet(() -> {
                return "0";
            });
            String mCode = Optional.ofNullable(shop.getMiddleCategory()).orElseGet(() -> {
                return "0";
            });
            String type = Optional.ofNullable(shop.getShopType()).orElseGet(() -> {
                return "0";
            });
            if (type.equals("1")) {
                shop.setShopAlias(tripCategoryService.getTripCategory(bCode, mCode, shop.getAddress()));
            } else {
                shop.setShopAlias(foodCategoryService.getShopCategory(bCode, mCode, shop.getAddress()));
            }
        }
        return shops;
    }

    public Map<String, Object> getSearchListByKeyword(String keyword, String shopType, int shopInterval, int idx) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        QueryBuilder qBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Shop.class)
                .get();

        Query luceneQuery = qBuilder
                .keyword()
                .wildcard()
                .onFields("shopName", "menu", "shopAlias", "shopCharge", "address", "roadAddress", "recommandMenu", "detailAddress")
                .matching("*" + keyword + "*")
                .createQuery();

        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Shop.class);

        List<Shop> result = fullTextQuery.getResultList();
        List<Shop> list = result.stream()
                .filter(e -> e.getShopType().equals(shopType))
                .collect(Collectors.toList());

        //list has only 1nd index(key:size, value:list)
        Map<String, Object> listBundle = new HashMap<>();
        listBundle.put("size", list.size());

        int left = idx * shopInterval, right = list.size() > idx * shopInterval + shopInterval ? idx * shopInterval + shopInterval : list.size();
        listBundle.put("payload", list.subList(left, right));

        return listBundle;
    }

    public Shop addShopInfo(Shop shops) {
        return shopRepository.save(shops);
//        return shops;
    }

    @Transactional
    public String updateShopInfo(ShopDTO shopDto, Integer shopId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow();
        Shop updated = shop.toEntity(shopDto, shopId);
        String viewName = null;
        if (shop.getShopType().equals("red")) {
            viewName = "admin-shop-red-list";
        } else {
            viewName = "admin-shop-green-list";
        }
        String server = "foodie.speedgabia.com";
        int port = 21;
        String user = "foodie";
        String pw = "a584472yscp@@";
        FTPClient con = null;
        Date from = new Date();
        SimpleDateFormat nowDateHHmmss = new SimpleDateFormat("HHmmss");
        SimpleDateFormat nowDateymd = new SimpleDateFormat("yyyyMMdd");
        String nowHHmmss = nowDateHHmmss.format(from);
        String nowymd = nowDateymd.format(from);
        ArrayList<String> images = new ArrayList<>();
        /*if(files.length > 0) {
            try {
                con = new FTPClient();
                con.setControlEncoding("utf-8");
                con.connect(server);
                if (con.login(user, pw)) {
                    con.enterLocalPassiveMode(); // important!
                    con.setFileType(FTP.BINARY_FILE_TYPE);

                    for (int i = 0; i < files.length; i++) {
                        if (!(files[i].getOriginalFilename().equals(""))) {
                            con.storeFile(files[i].getOriginalFilename(), files[i].getInputStream());
                            images.add("http://foodie.speedgabia.com/" + files[i].getOriginalFilename());
                        }
                    }

                    String result = new Gson().toJson(images);
                    shop.setMenuImages(result);
                    con.logout();
                    con.disconnect();
                }
            } catch (Exception e) {
                //                redirectAttributes.addFlashAttribute("message",
                //                        "Could not upload " + file.getOriginalFilename() + "!");
            }
        }*/
        Shop applied = shopRepository.save(updated);
        log.info("Successfully updated = {}", applied);
        return viewName;
    }


    public List<AdminListShop> getAdminShopList(String shopType, String a1, String a2, String a3,
                                                String s1, String s2, String s3, String option, String keyword, String couponFlag) {

        String jpql = "select r " +
                "from AdminListShop r, Shop s " +
                "where r.no = s.shopId " +
                "and s.shopType = " + shopType;

        if (!a1.equals("0")) jpql += " and r.area1st =" + Integer.parseInt(a1);
        if (!a2.equals("0")) jpql += " and r.area2nd =" + Integer.parseInt(a2);
        if (!a3.equals("0")) jpql += " and r.area3th =" + Integer.parseInt(a3);

        if (!s1.equals("0")) jpql += " and r.shop1st like '%" + s1 + "%'";
        if (!s2.equals("0")) jpql += " and r.shop2nd like '%" + s2 + "%'";
        if (!s3.equals("0")) jpql += " and r.shop3th like '%" + s3 + "%'";

        if (option.equals("업소명")) jpql += " and r.shopName like '%" + keyword + "%'";
        if (option.equals("등록자")) jpql += " and r.shopCharge like '%" + keyword + "%'";
        if (option.equals("아이디")) jpql += " and r.uid like '%" + keyword + "%'";
        if (option.equals("전화번호")) jpql += "and r.shopPhone like '%" + keyword + "%'";

        if (!couponFlag.equals("0")) jpql += " and r.couponFlag like '%no%'";

        javax.persistence.Query query = em.createQuery(jpql, AdminListShop.class);

        return query.getResultList();
    }
}