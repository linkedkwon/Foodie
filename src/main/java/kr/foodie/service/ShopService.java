package kr.foodie.service;

import kr.foodie.common.utils.ImageStrUtils;
import kr.foodie.domain.shopItem.AdminListShop;
import kr.foodie.domain.shopItem.Shop;
import kr.foodie.domain.shopItem.ShopDTO;
import kr.foodie.repo.ShopRepository;
import kr.foodie.service.admin.RegionAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.util.Strings;
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
    private final RegionAdminService regionAdminService;

    public void initializeHibernateSearch() {
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Shop> getSubwayShopInfos(Integer area1st, Integer area2st, Integer area3st, Integer shopType, int idx, int interval) {
        if (shopType.equals("1")) {
//            List<Integer> greenListRegionInfos = regionAdminRepository.findGreenRegionInfo(area1st,  area2st,  area3st);
            List<Shop> shops = shopRepository.findBySubwayTypeIdAndShopTypeOrderByUpdatedAt(area1st, area2st, area3st, shopType,
                    PageRequest.of(idx, interval, Sort.by("createdAt").descending())).getContent();
            return addAliasOnShops(shops);
        } else {
            List<Shop> shops = shopRepository.findBySubwayTypeIdAndShopTypeOrderByUpdatedAt(area1st, area2st, area3st, shopType,
                    PageRequest.of(idx, interval, Sort.by("createdAt").descending())).getContent();
            return addAliasOnShops(shops);
        }
    }

    public List<Shop> getShopInfos(Integer area2st, List<Integer> shopType, Boolean isGreen, int idx, int interval) {
        if (isGreen) {
            List<Shop> shops = shopRepository.findByArea1stAndShopTypeInAndPremiumRegisterDateIsNullOrderByUpdatedAt(area2st, shopType,
                    PageRequest.of(idx, interval, Sort.by("createdAt").descending())).getContent();
            return mainAliasOnShops(shops);
        }
        List<Shop> shops = shopRepository.findByArea2stAndShopTypeInAndPremiumRegisterDateIsNullOrderByUpdatedAt(area2st, shopType,
                PageRequest.of(idx, interval, Sort.by("createdAt").descending())).getContent();
        return mainAliasOnShops(shops);

    }

    public List<Shop> getSubwayPremiumShopInfos(Integer area1st, Integer area2st, Integer area3st, Integer shopType) {
        if (shopType.equals("1")) {
//            List<Integer> greenListRegionInfos = regionAdminRepository.findGreenRegionInfo(area1st,  area2st,  area3st);
//            List<Shop> shops = shopRepository.findBySubwayTypeIdAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(area1st,  area2st,  area3st, "1");
//            return addAliasOnShops(shops);
//        }else{
//            List<Shop> shops = shopRepository.findBySubwayTypeIdAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(area1st,  area2st,  area3st, "0");
//            return addAliasOnShops(shops);
        }
        return null;
    }


    public List<Shop> getShopPremiumInfos(Boolean isGreen, Integer area2st, List<Integer> shopType) {
        if (isGreen) {
            List<Shop> shops = shopRepository.findRandomByArea1stAndShopTypeInAndPremiumRegisterDateIsNull(area2st, shopType);
            return mainAliasOnShops(shops);
        }
        List<Shop> shops = shopRepository.findRandomByArea2stAndShopTypeInAndPremiumRegisterDateIsNull(area2st, shopType);
        return mainAliasOnShops(shops);
    }

  /*  페이지 있는경우
  public List<Shop> getShopPremiumInfos(int idx, int interval, Integer area2st,List<Integer> shopType) {
        if(shopType.equals("1")) {
//            List<Integer> greenListRegionInfos = regionAdminRepository.findGreenRegionInfo(area1st,  area2st,  area3st);
            List<Shop> shops = shopRepository.findByArea2stAndShopTypeInAndPremiumRegisterDateIsNullOrderByUpdatedAt(area2st, shopType, PageRequest.of(idx,interval,Sort.by("createdAt").descending())).getContent();
            return addAliasOnShops(shops);
        }else{
            List<Shop> shops = shopRepository.findByArea2stAndShopTypeInAndPremiumRegisterDateIsNullOrderByUpdatedAt(area2st, shopType, PageRequest.of(idx,interval,Sort.by("createdAt").descending())).getContent();
            return addAliasOnShops(shops);
        }
    }*/


    public void updateHit(Integer shopId, Integer count) {
        if (count == null) {
            count = 0;
        }
        shopRepository.updateFoodieHit(shopId, count + 1);
//        return duplicatedInfos;
//        return;
    }

    public List<Shop> getDuplicatedInfos(List<Integer> shopType, String shopName) {
        List<Shop> duplicatedInfos = shopRepository.findDuplicatedShop(shopType, shopName);
        return duplicatedInfos;
    }

    //    사이드에 그린리스트<-> 레드리스트 우선순위 지정필요
    public List<Shop> getShopInfosWithSideOrder(Boolean isGreen, Integer area2st, List<Integer> shopType) {
        if (isGreen) {
            List<Shop> shops = shopRepository.findByArea1stAndShopTypeIn(area2st, shopType);
            return mainAliasOnShops(shops);
        }
        List<Shop> shops = shopRepository.findByArea2stAndShopTypeIn(area2st, shopType);
        return mainAliasOnShops(shops);
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

    public List<Shop> getShopMainInfoByType(Integer type) {
        List<Shop> shops = shopRepository.findShopInfoByType(type);
        return mainAliasOnShops(shops);
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


    public int getItemSizeByRegionTypeAndShopType(Boolean isGreen, Integer area2st, List<Integer> shopType) {
        if (isGreen) {
            return shopRepository.countByArea1stAndShopTypeIn(area2st, shopType).orElseGet(() -> {
                return 0;
            });
        }
        return shopRepository.countByArea2stAndShopTypeIn(area2st, shopType).orElseGet(() -> {
            return 0;
        });
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
            String bCode = Optional.ofNullable(shop.getBigCategory()).orElseGet(() -> {
                return "0";
            });
            String mCode = Optional.ofNullable(shop.getMiddleCategory()).orElseGet(() -> {
                return "0";
            });
            String sCode = Optional.ofNullable(shop.getSmallCategory()).orElseGet(() -> {
                return "0";
            });
            Integer type = Optional.ofNullable(shop.getShopType()).orElseGet(() -> {
                return 0;
            });

            if (type.toString().equals("1")) {
                shop.setShopAlias(tripCategoryService.getTripCategory(bCode, mCode, sCode, shop.getAddress()));
            } else {
                shop.setShopAlias(foodCategoryService.getShopCategory(bCode, mCode, sCode, shop.getAddress()));
            }
            shop.setShopImage(extractShopImage(shop));
        }
        return shops;
    }

    protected List<Shop> mainAliasOnShops(List<Shop> shops) {
        for (Shop shop : shops) {
            String bCode = Optional.ofNullable(shop.getBigCategory()).orElseGet(() -> {
                return "0";
            });
            String mCode = Optional.ofNullable(shop.getMiddleCategory()).orElseGet(() -> {
                return "0";
            });
            String sCode = Optional.ofNullable(shop.getSmallCategory()).orElseGet(() -> {
                return "0";
            });
            if (shop.getSmallCategory() == null || shop.getSmallCategory().equals("")) {
                sCode = "0";
            }
            Integer type = Optional.ofNullable(shop.getShopType()).orElseGet(() -> {
                return 0;
            });

//            if (type.toString().equals("1")) {
//                shop.setShopAlias(tripCategoryService.getMainTripCategory(bCode, mCode, sCode));
//            } else {
            shop.setShopAlias(foodCategoryService.getMainShopCategory(bCode, mCode, sCode));
            if (shop.getFoodieLogRating() == null || shop.getFoodieLogRating().equals("없음")) {
                shop.setFoodieLogRating("");
            }
//            }

            shop.setShopImage(extractShopImage(shop));
        }
        return shops;
    }

    protected List<Shop> addListAliasOnShops(List<Shop> shops) {
        for (Shop shop : shops) {
            String bCode = Optional.ofNullable(shop.getBigCategory()).orElseGet(() -> {
                return "0";
            });
            String mCode = Optional.ofNullable(shop.getMiddleCategory()).orElseGet(() -> {
                return "0";
            });
            Integer type = Optional.ofNullable(shop.getShopType()).orElseGet(() -> {
                return 0;
            });

            if (type.toString().equals("1")) {
                shop.setShopAlias(tripCategoryService.getListTripCategory(bCode, mCode, shop.getAddress()));
            } else {
                shop.setShopAlias(foodCategoryService.getListShopCategory(bCode, mCode, shop.getAddress()));
            }
            shop.setShopImage(extractShopImage(shop));
        }
        return shops;
    }

    private String extractShopImage(Shop commentList) {
        String menuImgStr = commentList.getMenuImages();
        String replacedImg = "";
        if (commentList.getShopImage() != null) {
            replacedImg = commentList.getShopImage().strip();
            return replacedImg;
        }


        if (StringUtils.isNotBlank(menuImgStr)) {
            String[] images = menuImgStr
                    .replace("[", "")
                    .replace("]", "")
                    .replaceAll("\"", "")
                    .split(",");

            return Arrays.stream(images)
                    .findFirst()
                    .orElse(replacedImg);
        }

        return replacedImg;
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
                .filter(e -> e.getShopType() == Integer.parseInt(shopType))
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
    public String updateShopInfo(ShopDTO shopDto, Integer shopId, MultipartFile[] files, MultipartFile[] shopImagefiles, String existImages, String existShopImage) {
        Shop shop = shopRepository.findById(shopId).orElseThrow();
        Shop updated = shop.toEntity(shopDto, shopId);
        String viewName = null;
        Integer shopBackground = shop.getShopType();
        if (shopBackground == 1) {
            viewName = "redirect:/admin/shop/list/allred";
        } else if (shopBackground == 2) {
            viewName = "redirect:/admin/shop/list/allgreen";
        } else if (shopBackground == 3) {
            viewName = "redirect:/admin/shop/list/allred";
        } else if (shopBackground == 4) {
            viewName = "redirect:/admin/shop/list/allgreen";
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
        List<String> images = new ArrayList<>();
        List<String> existImageList = ImageStrUtils.strToList(existImages);


        if (shopImagefiles.length > 0) {
            try {
                con = new FTPClient();
                con.setControlEncoding("utf-8");
                con.connect(server, port);
                if (con.login(user, pw)) {
                    con.enterLocalPassiveMode(); // important!
                    con.setFileType(FTP.BINARY_FILE_TYPE);

                    if(existShopImage == null){
                        if(!StringUtils.equals(shopImagefiles[0].getOriginalFilename(), Strings.EMPTY)){
                            String shopImage = "http://foodie.speedgabia.com/" + shopImagefiles[0].getOriginalFilename();
                            con.storeFile(shopImagefiles[0].getOriginalFilename(), shopImagefiles[0].getInputStream());
                            updated.setShopImage(shopImage);
                        }
                    }else{
                        updated.setShopImage(existShopImage);
                    }
                    con.logout();
                    con.disconnect();
                }
            } catch (Exception e) {
                log.error("updateShopInfo error ", e);
            }
        }

        if (files.length > 0) {
            try {
                con = new FTPClient();
                con.setControlEncoding("utf-8");
                con.connect(server, port);
                if (con.login(user, pw)) {
                    con.enterLocalPassiveMode(); // important!
                    con.setFileType(FTP.BINARY_FILE_TYPE);

                    int existImageIdx = 0;



                    for (int i = 0; i < files.length; i++) {
                        int index = images.size();
                        if (!StringUtils.equals(files[i].getOriginalFilename(), Strings.EMPTY)) {
                            if(!(existImageList.contains("http://foodie.speedgabia.com/"+ files[i].getOriginalFilename()))) {
                                if (i >= images.size()) {
                                    images.add(index, "http://foodie.speedgabia.com/" + files[i].getOriginalFilename());
                                } else {
                                    images.set(i, "http://foodie.speedgabia.com/" + files[i].getOriginalFilename());
                                }
                            }
                        }
                        if (existImageIdx < existImageList.size()) {
                            images.add(existImageList.get(existImageIdx));
                            existImageIdx++;
                        }

                        con.storeFile(files[i].getOriginalFilename(), files[i].getInputStream());
                    }

                    String result = ImageStrUtils.listToStr(images);
                    updated.setMenuImages(result);
                    con.logout();
                    con.disconnect();
                }
            } catch (Exception e) {
                log.error("updateShopInfo error ", e);
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

    public void updateExistImage(Integer shopId, String existImages) {
        Shop entity = shopRepository.findByShopId(shopId);
        List<String> list = new ArrayList<>();
        list.add(existImages);
        entity.setMenuImages(list.toString());
        shopRepository.save(entity);
    }

    public void deleteShop(Integer shopId) {
        Shop entity = shopRepository.findByShopId(shopId);
        shopRepository.delete(entity);
    }
}
