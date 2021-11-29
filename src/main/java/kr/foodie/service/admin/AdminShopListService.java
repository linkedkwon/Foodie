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

        //1. bgcolor
        List<Integer> bg = getBgArray(vo.getBackground());
        jpql += "where s.shopType in :bg";

        //2. renderOption > !1 > filter
        /**
         * - 메모
         * 1. String 변수명 바꾸기 > 중복제거 > 함수화
         * 2. uid, hit(엔티티 필드 없음)
         * 3. 등록자 컬럼 필요(기존에 쓰던게 있었던거같음)
         */
        if(!vo.getRenderOption().equals("1")){
            if(!vo.getShop1st().equals("-1"))
                jpql += " and s.bigCategory like '%" + vo.getShop1st() + "%'";
            if(!vo.getShop2nd().equals("-1"))
                jpql += " and s.middleCategory like '%" + vo.getShop2nd() + "%'";
            if(!vo.getShop3th().equals("-1"))
                jpql += " and s.smallCategory like '%" + vo.getShop3th() + "%'";

            if(!(vo.getArea1st() == -1))
                jpql += " and s.area1st = " + vo.getArea1st();
            if(!(vo.getArea2nd() == -1))
                jpql += " and s.area2st = " + vo.getArea2nd();
            if(!(vo.getArea3th() == -1))
                jpql += " and s.area3st = " + vo.getArea3th();

            String selectedOption = vo.getSelectedOption();
            String keyword = vo.getKeyword();
            if(keyword.length() > 0) {
                if (selectedOption.equals("업소명"))
                    jpql += " and s.shopName like '%" + keyword + "%'";
                else if (selectedOption.equals("등록자"))
                    jpql += " and s.shopCharge like '%" + keyword + "%'";
                else if (selectedOption.equals("아이디"))
                    jpql += " and s.uid like '%" + keyword + "%'";
                else if (selectedOption.equals("전화번호"))
                    jpql += " and s.phone like '%" + keyword + "%'";
            }
        }

        //3. ordering
        String ordering = vo.getOrder();
        if(ordering.equals("수정일순")) { jpql += " order by s.updatedAt desc"; }
        else if(ordering.equals("조회순")) { jpql += " order by s.hit desc"; }
        else if(ordering.equals("평점순")){ jpql += " order by s.evaluation desc"; }
        else{ jpql += " order by s.createdAt desc"; }


        //4. query execute
        TypedQuery<Shop> query = em.createQuery(jpql, Shop.class)
                                    .setParameter("bg", bg)
                                    .setFirstResult((vo.getPage() - 1) * 15)
                                    .setMaxResults(15);

        return query.getResultList();
    }


    public int getCountList(AdminShopListVO vo){

        String jpql = "select count(s) from Shop s " +
                      "where s.shopType in :bg";
        if(!vo.getRenderOption().equals("1")){
            if(!vo.getShop1st().equals("-1"))
                jpql += " and s.bigCategory like '%" + vo.getShop1st() + "%'";
            if(!vo.getShop2nd().equals("-1"))
                jpql += " and s.middleCategory like '%" + vo.getShop2nd() + "%'";
            if(!vo.getShop3th().equals("-1"))
                jpql += " and s.smallCategory like '%" + vo.getShop3th() + "%'";

            if(!(vo.getArea1st() == -1))
                jpql += " and s.area1st = " + vo.getArea1st();
            if(!(vo.getArea2nd() == -1))
                jpql += " and s.area2st = " + vo.getArea2nd();
            if(!(vo.getArea3th() == -1))
                jpql += " and s.area3st = " + vo.getArea3th();

            String selectedOption = vo.getSelectedOption();
            String keyword = vo.getKeyword();
            if(keyword.length() > 0) {
                if (selectedOption.equals("업소명"))
                    jpql += " and s.shopName like '%" + keyword + "%'";
                else if (selectedOption.equals("등록자"))
                    jpql += " and s.shopCharge like '%" + keyword + "%'";
                else if (selectedOption.equals("아이디"))
                    jpql += " and s.uid like '%" + keyword + "%'";
                else if (selectedOption.equals("전화번호"))
                    jpql += " and s.phone like '%" + keyword + "%'";
            }
        }

        Query query = em.createQuery(jpql)
                .setParameter("bg", getBgArray(vo.getBackground()));

        return Math.toIntExact((long) query.getSingleResult());
    }


    public List<Integer> getPages(AdminShopListVO vo){

        List<Integer> pages = new ArrayList<>();
        int count = getCountList(vo);
        int pageSize = count / 15;
        pageSize += count % 15 > 0 ? 1 : 0;

        int left = 1;
        if(vo.getRenderOption().equals("1") || vo.getPage() == 1) {
            left = 1;
        }
        else{
            int currentPage = vo.getPage();
            left = (currentPage / 10) * 10 + 1;

        }

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
