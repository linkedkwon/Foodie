package kr.foodie.repo;

import kr.foodie.domain.shopItem.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    Shop findByShopId(Integer shopId);

    Page<Shop> findByShopIdOrderByUpdatedAt(Integer shopId, Pageable pageable);
    Page<Shop> findByArea1stAndArea2stAndArea3stAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(Integer area1st, Integer area2st, Integer area3st, String shopType, Pageable pageable);
//    Page<Shop> findByArea1stAndArea2stAndArea3stInAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(Integer area1st, Integer area2st, Integer area3st , String shopType, Pageable pageable);
//    List<Shop> findByArea1stAndArea2stAndArea3stAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(Integer area1st, Integer area2st, Integer area3st, String shopType);
    List<Shop> findByArea1stAndArea2stAndArea3stAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(Integer area1st, Integer area2st, Integer area3st , String shopType);

//    Page<Shop> findBySubwayTypeIdAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(Integer area1st, Integer area2st, Integer area3st, String shopType, Pageable pageable);
    Page<Shop> findBySubwayTypeIdAndShopTypeOrderByUpdatedAt(Integer area1st, Integer area2st, Integer area3st, String shopType, Pageable pageable);
//    List<Shop> findBySubwayTypeIdAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(Integer area1st, Integer area2st, Integer area3st, String shopType);
    List<Shop> findByArea1stAndArea2stAndArea3stAndShopType(Integer area1st, Integer area2st, Integer area3st, String shopType);

//    List<Shop> findByArea1stAndArea2stAndArea3stAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(Integer area1st, Integer area2st, Integer area3st, String shopType);
//    List<Shop> findByArea1stAndArea2stAndArea3stAndShopType(Integer area1st, Integer area2st, Integer area3st, String shopType);

    List<Shop> findTop4ByArea1stAndArea2stAndArea3stAndShopType(Integer area1st, Integer area2st, Integer area3st, String shopType);
//    List<Shop> findByShopId(Integer shopId);

    @Query(value="select * from shop where shop_type=?1 and region_id=?2 and theme_list like %?3%", nativeQuery = true)
    List<Shop> findByShopTypeAndRegionAndThemeList(String shopTypeId, Integer area1st, Integer area2st, Integer area3st, String Filter);

    // search - 가게이름
    @Query(value="select * from shop where shop_type=?1 and shop_name like %?2%", nativeQuery = true)
    List<Shop> findByShopTypeAndShopName(String shopTypeId, String Filter);

    // search - 내용
    @Query(value="select * from shop where shop_type=?1 and history like %?2%", nativeQuery = true)
    List<Shop> findByShopTypeAndHistory(String shopTypeId, String Filter);

    // search - 주소
    @Query(value="select * from shop where shop_type=?1 and address like %?2%", nativeQuery = true)
    List<Shop> findByShopTypeAndAddress(String shopTypeId, String Filter);


    Optional<Integer> countByArea1stAndArea2stAndArea3stAndShopType(Integer area1st, Integer area2st, Integer area3st, String shopType);


    @Query(value="select * from rankup_shop_introduce_data where no in (select shop_id from main_board where type = ?1)", nativeQuery = true)
//    @Query(value="select s.*, r.* from rankup_shop_introduce_data s left join epicure_region r on s.area_1st = r.no where s.no in (select shop_id from main_board where type = ?1)", nativeQuery = true)
    List<Shop> findShopInfoByType(Integer type);

    @Query(value="select * from shop where address like %?1% and shop_id not in (select shop_id from main_board where type=?2)", nativeQuery = true)
    List<Shop> getShopInfoByAddressName(String address, Integer type);

    @Query(value="SELECT * ,(6371*acos(cos(radians(?1))*cos(radians(slLat))*cos(radians(slLng)-radians(?2))+sin(radians(?1))*sin(radians(slLat)))) AS distance from shop where shop_type = ?3 having distance <= 20 order by distance", nativeQuery = true)
    List<Shop> findByAddressContaining(String lat, String lng, String shopType);

    //admin
    List<Shop> findByShopType(String shopType);
    List<Shop> findByShopTypeAndShopTypeIn(String shopType, List<String> background);


    //임시로 만듬
    List<Shop> findByShopTypeInOrderByShopIdDesc(List<Integer> shopType);


    List<Shop> findByShopTypeIn(List<Integer> shopType);
    List<Shop> findByBigCategoryAndShopType(Integer bCode, String shopType);
    List<Shop> findByBigCategoryAndMiddleCategoryAndShopType(Integer bCode, Integer mCode, String shopType);
    List<Shop> findByBigCategoryAndMiddleCategoryAndSmallCategoryAndShopType(Integer bCode, Integer mCode, Integer sCode, String shopType);

    // region
    List<Shop> findByShopTypeAndArea1stAndArea2stAndArea3st(String shopType, Integer area1st, Integer area2st, Integer area3st);
    List<Shop> findByBigCategoryAndShopTypeAndArea1stAndArea2stAndArea3st(Integer bCode, String shopType, Integer area1st, Integer area2st, Integer area3st);
    List<Shop> findByBigCategoryAndMiddleCategoryAndShopTypeAndArea1stAndArea2stAndArea3st(Integer bCode, Integer mCode, String shopType, Integer area1st, Integer area2st, Integer area3st);
    List<Shop> findByBigCategoryAndMiddleCategoryAndSmallCategoryAndShopTypeAndArea1stAndArea2stAndArea3st(Integer bCode, Integer mCode, Integer sCode, String shopType, Integer area1st, Integer area2st, Integer area3st);

    @Transactional
    @Modifying
    @Query(value="insert into main_board(shop_id, type) values (?1, ?2)", nativeQuery = true)
    void insertMainRecommand(Integer shopId, Integer type);

    @Transactional
    @Modifying
    @Query(value="delete from main_board where shop_id = ?1 and type = ?2", nativeQuery = true)
    void deleteMainRecommand(Integer shopId, Integer type);


    @Query(value="select * from shop where shop_type = ?1 and replace(shop_name,' ','') like %?2%", nativeQuery = true)
    List<Shop> findDuplicatedShop(String shopType, String shopName);

    Optional<Object> findByShopIdOrderByUpdatedAt(Integer shopId);
}
