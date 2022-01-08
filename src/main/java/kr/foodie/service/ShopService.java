package kr.foodie.service;

import com.google.gson.Gson;
import kr.foodie.domain.shopItem.AdminListShop;
import kr.foodie.domain.shopItem.Shop;
import kr.foodie.domain.shopItem.ShopDTO;
import kr.foodie.repo.ShopRepository;
import kr.foodie.repo.admin.RegionAdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
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
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public List<Shop> getSubwayShopInfos(Integer area1st, Integer area2st, Integer area3st, String shopType, int idx, int interval) {
        if(shopType.equals("1")){
            List<Integer> greenListRegionInfos = regionAdminRepository.findGreenRegionInfo(area1st,  area2st,  area3st);
            List<Shop> shops = shopRepository.findBySubwayTypeIdAndShopTypeOrderByUpdatedAt(area1st,  area2st,  area3st, shopType,
                    PageRequest.of(idx,interval,Sort.by("createdAt").descending())).getContent();
            return addAliasOnShops(shops);
        }else{
            List<Shop> shops = shopRepository.findBySubwayTypeIdAndShopTypeOrderByUpdatedAt(area1st,  area2st,  area3st, shopType,
                    PageRequest.of(idx,interval,Sort.by("createdAt").descending())).getContent();
            return addAliasOnShops(shops);
        }
    }

    public List<Shop> getShopInfos(Integer area1st, Integer area2st, Integer area3st, String shopType, int idx, int interval) {
        if(shopType.equals("1")){
            List<Integer> greenListRegionInfos = regionAdminRepository.findGreenRegionInfo(area1st,  area2st,  area3st);
            List<Shop> shops = shopRepository.findByArea1stAndArea2stAndArea3stAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(area1st,  area2st,  area3st, shopType,
                    PageRequest.of(idx,interval,Sort.by("createdAt").descending())).getContent();
            return addAliasOnShops(shops);
        }else{
            List<Shop> shops = shopRepository.findByArea1stAndArea2stAndArea3stAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(area1st,  area2st,  area3st, shopType,
                    PageRequest.of(idx,interval,Sort.by("createdAt").descending())).getContent();
            return addAliasOnShops(shops);
        }
    }

    public List<Shop> getSubwayPremiumShopInfos(Integer area1st, Integer area2st, Integer area3st, String shopType) {
        if(shopType.equals("1")) {
            List<Integer> greenListRegionInfos = regionAdminRepository.findGreenRegionInfo(area1st,  area2st,  area3st);
//            List<Shop> shops = shopRepository.findBySubwayTypeIdAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(area1st,  area2st,  area3st, "1");
//            return addAliasOnShops(shops);
//        }else{
//            List<Shop> shops = shopRepository.findBySubwayTypeIdAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(area1st,  area2st,  area3st, "0");
//            return addAliasOnShops(shops);
        }
        return null;
    }


    public List<Shop> getShopPremiumInfos(Integer area1st, Integer area2st, Integer area3st, String shopType) {
        if(shopType.equals("1")) {
            List<Integer> greenListRegionInfos = regionAdminRepository.findGreenRegionInfo(area1st,  area2st,  area3st);
            List<Shop> shops = shopRepository.findByArea1stAndArea2stAndArea3stAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(area1st,  area2st,  area3st, shopType);
            return addAliasOnShops(shops);
        }else{
            List<Shop> shops = shopRepository.findByArea1stAndArea2stAndArea3stAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(area1st,  area2st,  area3st, shopType);
            return addAliasOnShops(shops);
        }
    }


    public void updateHit(Integer shopId, Integer count) {
        if(count == null){
            count=0;
        }
        shopRepository.updateFoodieHit(shopId, count+1);
//        return duplicatedInfos;
//        return;
    }
    public List<Shop> getDuplicatedInfos(List<Integer> shopType, String shopName) {
        List<Shop> duplicatedInfos = shopRepository.findDuplicatedShop(shopType, shopName);
        return duplicatedInfos;
    }

    //    사이드에 그린리스트<-> 레드리스트 우선순위 지정필요
    public List<Shop> getShopInfosWithSideOrder(Integer area1st, Integer area2st, Integer area3st, String shopType) {
        if(shopType.equals("1")){
            List<Shop> shops = shopRepository.findByArea1stAndArea2stAndArea3stAndShopType(area1st, area2st, area3st, "0");
            return addAliasOnShops(shops);
        }else{
            List<Shop> shops = shopRepository.findByArea1stAndArea2stAndArea3stAndShopType(area1st, area2st, area3st, "1");
            return addAliasOnShops(shops);
        }
    }

    public Shop getShopDetail(Integer shopId) {
        return (Shop) shopRepository.findByShopIdOrderByUpdatedAt(shopId).orElseThrow();
    }

    public List<Shop> getFilterShopList(String shopTypeId, Integer area1st, Integer area2st, Integer area3st, String filterItems) {
        return shopRepository.findByShopTypeAndRegionAndThemeList(shopTypeId, area1st, area2st, area3st, filterItems);
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


    public int getItemSizeByRegionTypeAndShopType(Integer area1st, Integer area2st, Integer area3st, String shopType){
        return shopRepository.countByArea1stAndArea2stAndArea3stAndShopType(area1st, area2st, area3st, shopType).orElseGet(()->{return 0;});
    }

    public List<Shop> getTop50AdminShopInfos(List<Integer> background) {
        //등록되는거 오름차순으로 보여줄려고 임시로 만듬
        return shopRepository.findByShopTypeInOrderByShopIdDesc(background);
        //return shopRepository.findByShopTypeIn(background);
    }
    //rderByUpdatedAtDesc
    public List<Shop> getAllShopInfos(List<Integer> shopType) {
        return shopRepository.findByShopTypeIn(shopType);
    }

    protected List<Shop> addAliasOnShops(List<Shop> shops) {
        for (Shop shop : shops) {
            Integer bCode = Optional.ofNullable(shop.getArea1st()).orElseGet(() -> {
                return 0;
            });
            Integer mCode = Optional.ofNullable(shop.getArea2st()).orElseGet(() -> {
                return 0;
            });
            Integer type = Optional.ofNullable(shop.getShopType()).orElseGet(() -> {
                return 0;
            });
//            if (type.toString().equals("1")) {
//                shop.setShopAlias(tripCategoryService.getTripCategory(bCode, mCode, shop.getAddress()));
//            } else {
//                shop.setShopAlias(foodCategoryService.getShopCategory(bCode.toString(), mCode.toString(), shop.getAddress()));
//            }
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
    }

    @Transactional
    public String updateShopInfo(ShopDTO shopDto, Integer shopId, MultipartFile[] files, String existImages) {
        Shop shop = shopRepository.findById(shopId).orElseThrow();
        Shop updated = shop.toEntity(shopDto, shopId);
        String viewName = null;
        Integer shopBackground = shop.getShopType();
        if (shopBackground == 1) {
            viewName = "redirect:/admin/shop/list/red";
        } else if (shopBackground == 2) {
            viewName = "redirect:/admin/shop/list/green";
        } else if (shopBackground == 3) {
            viewName = "redirect:/admin/shop/list/mustard";
        } else if (shopBackground == 4) {
            viewName = "redirect:/admin/shop/list/mint";
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
        String[] items=null;
        if (files.length > 0) {
            try {
                con = new FTPClient();
                con.setControlEncoding("utf-8");
                con.connect(server, port);
                if (con.login(user, pw)) {
                    con.enterLocalPassiveMode(); // important!
                    con.setFileType(FTP.BINARY_FILE_TYPE);


//                    Gson gson = new Gson();
//                    JsonObject convertedObject = new Gson().fromJson(existImages, JsonObject.class);
//                    Map<String, Object> map = gson.fromJson(existImages, Map.class);
                    if(existImages != null){
                        items = existImages.replace("[", "").replace("]", "").replaceAll("\"", "").split(",");
                        for(int i=0; i < items.length; i++){
                            if(!(items[i].equals(""))){
                                images.add(items[i]);
                            }
                        }
                    }

                    for (int i = 0; i < files.length; i++) {
                        if (!(files[i].getOriginalFilename().equals(""))) {
                            con.storeFile(files[i].getOriginalFilename(), files[i].getInputStream());
                            images.add("http://foodie.speedgabia.com/" + files[i].getOriginalFilename());
                        }
                    }

                    String result = new Gson().toJson(images);

                    updated.setMenuImages(result);
                    con.logout();
                    con.disconnect();
                }
            } catch (Exception e) {
            }
        }
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
        if (!a2.equals("0")) jpql += " and r.area2st =" + Integer.parseInt(a2);
        if (!a3.equals("0")) jpql += " and r.area3st =" + Integer.parseInt(a3);

        if (!s1.equals("0")) jpql += " and r.bigCategory like '%" + s1 + "%'";
        if (!s2.equals("0")) jpql += " and r.middleCategory like '%" + s2 + "%'";
        if (!s3.equals("0")) jpql += " and r.smallCategory like '%" + s3 + "%'";

        if (option.equals("업소명")) jpql += " and r.shopName like '%" + keyword + "%'";
        if (option.equals("등록자")) jpql += " and r.shopCharge like '%" + keyword + "%'";
        if (option.equals("아이디")) jpql += " and r.uid like '%" + keyword + "%'";
        if (option.equals("전화번호")) jpql += "and r.shopPhone like '%" + keyword + "%'";

        if (!couponFlag.equals("0")) jpql += " and r.couponFlag like '%no%'";

        javax.persistence.Query query = em.createQuery(jpql, AdminListShop.class);

        return query.getResultList();
    }

    public void updateExistImage(Integer shopId, String existImages){
        Shop entity = shopRepository.findByShopId(shopId);
        List<String> list = new ArrayList<>();
        list.add(existImages);
        entity.setMenuImages(list.toString());
        shopRepository.save(entity);
    }

    public void deleteShop(Integer shopId){
        Shop entity = shopRepository.findByShopId(shopId);
        shopRepository.delete(entity);
    }
}