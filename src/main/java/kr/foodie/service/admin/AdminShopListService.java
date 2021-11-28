package kr.foodie.service.admin;

import kr.foodie.domain.shopItem.AdminShopListVO;
import kr.foodie.domain.shopItem.Shop;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

@Service
public class AdminShopListService {

    @PersistenceContext
    EntityManager em;

    public List<Shop> getAdminShopList(AdminShopListVO vo){
        String jpql = "select s from Shop s ";

        //1. background color
        List<Integer> bg = getBgArray(vo.getBackground());

        //2. renderOption > 1 > all list
        if(vo.getRenderOption().equals("1")){
            jpql += "where s.shopType in :bg " +
                    "order by s.createdAt desc";
        }

        //3. query execute
        TypedQuery<Shop> query = em.createQuery(jpql, Shop.class)
                                    .setParameter("bg", bg)
                                    .setFirstResult((vo.getPage() - 1) * 15)
                                    .setMaxResults(15);

        return query.getResultList();
    }

    public int getCountList(AdminShopListVO vo){

        String jpql = "select count(s) from Shop s ";
        if(vo.getRenderOption().equals("1"))
            jpql += "where s.shopType in :bg ";

        Query query = em.createQuery(jpql)
                .setParameter("bg", getBgArray(vo.getBackground()));

        return Math.toIntExact((long) query.getSingleResult());
    }

    public List<Integer> getPages(AdminShopListVO vo){

        List<Integer> pages = new ArrayList<>();
        int count = getCountList(vo);
        int pageSize = count / 15;
        pageSize += count % 15 > 0 ? 1 : 0;

        String str = Integer.toString(count);
        int left = Integer.parseInt(str.substring(0, str.length() - 2) + "1");

        for(int i = left; i <= pageSize; i++){
            if(i == left + 10)
                break;
            pages.add(i);
        }

        return pages;
    }

    public Map<String, Integer> getBtnPages(AdminShopListVO vo){
        int count = getCountList(vo);
        int pageSize = count / 15;
        pageSize += count % 15 > 0 ? 1 : 0;

        int page = vo.getPage(), nextFlag = (page / 10) * 10 + 11;

        Map<String, Integer> btnValues = new HashMap<>();
        btnValues.put("first", page - 11 >= 0 ? 1:-1);
        btnValues.put("last", nextFlag <= pageSize ? pageSize : -1);
        btnValues.put("prev", page - 11 >= 0 ? (page / 10 - 1) * 10 + 1 : -1);
        btnValues.put("next", nextFlag <= pageSize ? nextFlag : -1);

        return btnValues;
    }

    private List<Integer> getBgArray(String background){

        List<Integer> bg = null;

        if(background.equals("allgreen"))
            bg = Arrays.asList(2, 4);
        else if(background.equals("allred"))
            bg = Arrays.asList(1, 3);
        else
            bg = Arrays.asList(Integer.parseInt(background));

        return bg;
    }
}
