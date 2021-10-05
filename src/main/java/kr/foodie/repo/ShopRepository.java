package kr.foodie.repo;

import kr.foodie.domain.category.Theme;
import kr.foodie.domain.shop.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Page<Shop> findByRegionIdAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(Integer regionId, String shopType, Pageable pageable);
    Page<Shop> findByRegionIdInAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(List<Integer> regionId , String shopType, Pageable pageable);
    Page<Shop> findBySubwayTypeIdAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(Integer regionId, String shopType, Pageable pageable);
    Page<Shop> findBySubwayTypeIdInAndShopTypeAndPremiumRegisterDateIsNullOrderByUpdatedAt(List<Integer> regionId, String shopType, Pageable pageable);
    List<Shop> findBySubwayTypeIdAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(Integer regionId, String shopType);
    List<Shop> findBySubwayTypeIdInAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(List<Integer> regionId, String shopType);

    List<Shop> findByRegionIdAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(Integer regionId, String shopType);
    List<Shop> findByRegionIdInAndShopTypeAndPremiumRegisterDateIsNotNullOrderByPremiumRegisterDateDesc(List<Integer> regionId, String shopType);

    List<Shop> findTop4ByRegionIdAndShopType(Integer regionId, String shopType);
    List<Shop> findByShopId(Integer shopId);

    @Query(value="select * from shop where shop_type=?1 and region_id=?2 and theme_list like %?3%", nativeQuery = true)
    List<Shop> findByShopTypeAndRegionAndThemeList(String shopTypeId, Integer regionId, String Filter);

    // search - 가게이름
    @Query(value="select * from shop where shop_type=?1 and shop_name like %?2%", nativeQuery = true)
    List<Shop> findByShopTypeAndShopName(String shopTypeId, String Filter);

    // search - 내용
    @Query(value="select * from shop where shop_type=?1 and history like %?2%", nativeQuery = true)
    List<Shop> findByShopTypeAndHistory(String shopTypeId, String Filter);

    // search - 주소
    @Query(value="select * from shop where shop_type=?1 and address like %?2%", nativeQuery = true)
    List<Shop> findByShopTypeAndAddress(String shopTypeId, String Filter);


    Optional<Integer> countByRegionIdAndShopType(Integer regionId, String shopType);


//    @Query(value="select * from shop where shop_id in (select shop_id from main_board where type = ?1)", nativeQuery = true)
    @Query(value="select s.*, r.* , f.* from shop s left join region r on s.region_id = r.region_id left join food_category f on s.big_category = f.bcode where shop_id in (select shop_id from main_board where type = ?1) and f.level=1", nativeQuery = true)
    List<Shop> findShopInfoByType(Integer type);

    @Query(value="select * from shop where address like %?1% and shop_id not in (select shop_id from main_board where type=?2)", nativeQuery = true)
    List<Shop> getShopInfoByAddressName(String address, Integer type);

    @Query(value="SELECT * ,(6371*acos(cos(radians(?1))*cos(radians(slLat))*cos(radians(slLng)-radians(?2))+sin(radians(?1))*sin(radians(slLat)))) AS distance from shop where shop_type = ?3 having distance <= 20 order by distance", nativeQuery = true)
    List<Shop> findByAddressContaining(String lat, String lng, String shopType);

    //admin
    List<Shop> findByShopType(String shopType);
    List<Shop> findTop50ByShopType(String shopType);
    List<Shop> findByBigCategoryAndShopType(Integer bCode, String shopType);
    List<Shop> findByBigCategoryAndMiddleCategoryAndShopType(Integer bCode, Integer mCode, String shopType);
    List<Shop> findByBigCategoryAndMiddleCategoryAndSmallCategoryAndShopType(Integer bCode, Integer mCode, Integer sCode, String shopType);

    // region
    List<Shop> findByShopTypeAndRegionId(String shopType, Integer regionId);
    List<Shop> findByBigCategoryAndShopTypeAndRegionId(Integer bCode, String shopType, Integer regionId);
    List<Shop> findByBigCategoryAndMiddleCategoryAndShopTypeAndRegionId(Integer bCode, Integer mCode, String shopType, Integer regionId);
    List<Shop> findByBigCategoryAndMiddleCategoryAndSmallCategoryAndShopTypeAndRegionId(Integer bCode, Integer mCode, Integer sCode, String shopType, Integer regionId);

    @Transactional
    @Modifying
    @Query(value="insert into main_board(shop_id, type) values (?1, ?2)", nativeQuery = true)
    void insertMainRecommand(Integer shopId, Integer type);

    @Transactional
    @Modifying
    @Query(value="delete from main_board where shop_id = ?1 and type = ?2", nativeQuery = true)
    void deleteMainRecommand(Integer shopId, Integer type);
}
