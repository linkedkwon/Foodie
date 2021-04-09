package kr.foodie.service;

import kr.foodie.domain.account.FavoriteShop;
import kr.foodie.domain.account.Pagination;
import kr.foodie.domain.shop.Shop;
import kr.foodie.repo.FavoriteShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;


@Service
public class FavoriteShopService {

    private static final int interval = 4;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private FavoriteShopRepository favoriteShopRepository;


    public String addItem(int userId, int shopId){

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

    @Transactional
    public String deleteItem(int userId, int shopId){
        favoriteShopRepository.deleteByUserIdAndShopId(userId, shopId);
        return "1";
    }

    @Transactional
    public String deleteAllItem(int userId){
        favoriteShopRepository.deleteAllByUserId(userId);
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

        System.out.println("샵임\n"+query.getResultList());

        return query.getResultList();
    }

    public List<Pagination> getPagination(int size, int idx){

        int len = getLen(size);
        int lef = (idx / interval) * interval;
        String path = "/user/favorite/";
        List<Pagination> paginations = new ArrayList<>();

        int cnt = 0;
        String classValue = "";
        for(int i=lef; i<len;i++){
            if(cnt++ == interval)
                return paginations;
            if(i == idx)
                classValue = "active";

            paginations.add(Pagination.builder()
                                .idx(i+1)
                                .path(path+i)
                                .classValue(classValue)
                                .build());
            classValue = "";
        }
        return paginations;
    }

    public Map<String, String> getPaginationBtn(int size, int idx){

        int len = getLen(size);
        idx = idx/interval;
        String path = "/user/favorite/";
        Map<String, String> maps = new HashMap<String, String>();

        String prev = idx > 0 ? path+Integer.toString((idx-1) * interval): "javascript:void(0)";
        String next = idx < len/4 ? path+Integer.toString((idx+1) * interval) : "javascript:void(0)";

        maps.put("prev",prev);
        maps.put("next",next);

        return maps;
    }

    public static int getLen(int size){
        return (size % 5 == 0)? size/5 : size/5+1;
    }
}
