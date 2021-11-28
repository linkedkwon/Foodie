package kr.foodie.domain.shopItem;

import lombok.Data;

@Data
public class AdminShopListVO {

    //컬러
    private String background;

    //조회 옵션 1:전체, 2:페이지네이션, 검색 동일
    private String renderOption;


    //여해지 카테고리, 지역
    private String shop1st;
    private String shop2nd;
    private String shop3th;
    private int area1st;
    private int area2nd;
    private int area3th;

    //검색어
    private String selectedOption;
    private String keyword;

    //쿠폰 여부
    private String couponFlag;

    //현재 페이지
    private Integer page;
}
