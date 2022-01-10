package kr.foodie.service.admin;

import kr.foodie.domain.account.AdminReviewListVO;
import kr.foodie.domain.account.Review;
import kr.foodie.domain.account.ReviewDTO;
import kr.foodie.domain.user.User;
import kr.foodie.repo.ReviewRepository;
import kr.foodie.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class AdminReviewService {

    @PersistenceContext
    private EntityManager em;

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    private static final String join = "from Review r left join User u "
                            +"on r.userId = u.id "
                            +"left join Shop s "
                            +"on s.shopId = r.shopId";

    public List<ReviewDTO> getAdminReviewList(AdminReviewListVO vo){
        String jpql = "select new kr.foodie.domain.account.ReviewDTO("
                +"r.shopId, s.shopName,r.userId,  u.name,  r.reviewId, r.starRating, r.content, r.bestComment, r.point, "
                +"function('date_format', r.createdTime, '%Y-%m-%d')) " + join;

        TypedQuery<ReviewDTO> query = em.createQuery(addQueryByFilter(vo, jpql), ReviewDTO.class)
                .setFirstResult((vo.getPage() - 1) * 15)
                .setMaxResults(15);

        return query.getResultList();
    }

    public int getAllReviewCount(AdminReviewListVO vo){
        String jpql = "select count(r) " + join;
        Query query = em.createQuery(addQueryByFilter(vo, jpql));

        return Math.toIntExact((long) query.getSingleResult());
    }

    public List<Integer> getPages(AdminReviewListVO vo, int size){

        List<Integer> pages = new ArrayList<>();
        int pageSize = size / 15;
        pageSize += size % 15 > 0 ? 1 : 0;

        int left = 1;
        if(vo.getPage() == 1) {
            left = 1;
        }
        else{
            int currentPage = vo.getPage();
            left = currentPage % 10 == 0 ?
                    (currentPage / 10 - 1) * 10 + 1 : (currentPage / 10) * 10 + 1;
        }

        for(int i = left; i <= pageSize; i++){
            if(i == left + 10)
                break;
            pages.add(i);
        }
        return pages;
    }

    public Map<String, Integer> getBtnPages(AdminReviewListVO vo, int size){
        int pageSize = size / 15;
        pageSize += size % 15 > 0 ? 1 : 0;

        int page = vo.getPage();

        int nextFlag = page % 10 == 0 ? (page / 10 - 1) * 10 + 11 : (page / 10) * 10 + 11;
        int prevFlag = page % 10 == 0 ? (page / 10 - 2) * 10 + 1: (page / 10 - 1) * 10 + 1;

        Map<String, Integer> btnValues = new HashMap<>();
        btnValues.put("first", page - 11 >= 0 ? 1 : -1);
        btnValues.put("prev", page - 11 >= 0 ? prevFlag : -1);
        btnValues.put("last", nextFlag <= pageSize ? pageSize : -1);
        btnValues.put("next", nextFlag <= pageSize ? nextFlag : -1);

        return btnValues;
    }

    private String addQueryByFilter(AdminReviewListVO vo, String jpql){
        boolean hasWhereCaluse = false;

        if(!vo.getKeyword().equals("-1")) {
            if (vo.getOption().equals("이름")) {
                jpql += " where u.name like '%" + vo.getKeyword() + "%'";
                hasWhereCaluse = true;
            }
            else if(vo.getOption().equals("가게명")) {
                jpql += " where s.shopName like '%" + vo.getKeyword() + "%'";
                hasWhereCaluse = true;
            }
            else if(vo.getOption().equals("내용")) {
                jpql += " where r.content like '%" + vo.getKeyword() + "%'";
                hasWhereCaluse = true;
            }
        }

        if(!vo.getBestOption().equals("-1")) {
            if(!hasWhereCaluse)
                jpql += " where r.bestComment like '%" + vo.getBestOption() + "%'";
            else
                jpql += " and r.bestComment like '%" + vo.getBestOption() + "%'";
        }

        jpql += " order by r.createdTime desc";

        return jpql;
    }

    @Transactional
    public String deleteUserById(List<Integer> list){
        for(int o : list){
            reviewRepository.deleteByReviewId(o);
        }
        return "1";
    }

    public String switchReview(ReviewDTO dto){

        Optional<User> user = userRepository.findUserById(dto.getUserId());
        Optional<Review> entity = reviewRepository.findById(dto.getReviewId());
        int givenPoint = dto.getPoint();
        int entityPoint = entity.get().getPoint();
        int userPoint = user.get().getPoint();

        if(dto.getBestComment().equals("0")){
            entity.get().setBestComment("1");
            entity.get().setPoint(entityPoint + givenPoint);
            user.get().setPoint(userPoint + givenPoint);
        }
        else{
            entity.get().setBestComment("0");
            int cal = entityPoint - givenPoint;
            entity.get().setPoint(cal < 0 ? 0 : cal);
            cal = userPoint - givenPoint;
            user.get().setPoint(cal < 0 ? 0 : cal);
        }

        reviewRepository.save(entity.get());
        userRepository.save(user.get());

        return "1";
    }
}
