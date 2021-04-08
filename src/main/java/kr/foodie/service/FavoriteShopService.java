package kr.foodie.service;

import kr.foodie.domain.account.FavoriteShop;
import kr.foodie.domain.shop.Shop;
import kr.foodie.repo.FavoriteShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.List;

@Service
public class FavoriteShopService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private FavoriteShopRepository favoriteShopRepository;

    public String addFavoriteShop(int userId, int shopId){

        FavoriteShop entity = favoriteShopRepository.findByUserIdAndShopId(userId, shopId)
                .orElseGet(() -> {
                    return FavoriteShop.builder()
                            .userId(userId)
                            .shopId(shopId)
                            .build();
                });

        entity.setCreatedTime(Calendar.getInstance().getTime());
        favoriteShopRepository.save(entity);

        return "1";
    }

    public int getPageSize(int userId){
        return favoriteShopRepository.countByUserId(userId).orElseGet(()->{return 0;});
    }

    public List<Shop> getFavoriteShops(int userId, int idx){

        String jpql = "select s "
                +"from Shop s, FavoriteShop f "
                +"where s.shopId = f.shopId and f.userId = " + userId
                +"order by f.createdTime desc";

        Query query = em.createQuery(jpql);
        query.setFirstResult(idx*5);
        query.setMaxResults(5);

        return query.getResultList();
    }
}
