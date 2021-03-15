package kr.foodie.domain.account.model;

import kr.foodie.domain.detail.model.Detail;
import kr.foodie.domain.member.model.Member;

import javax.persistence.*;

@Entity
@Table(name = "FAVORITES")
public class Favorites {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAVORITES_ID")
    private Long id;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "DETAIL_ID")
    private Long detailId;

    @Column(name = "FAVORITES_TITLE")
    private String title;

    @Column(name = "FAVORITES_CATEGORY")
    private String category;

    @Column(name = "FAVORITES_ADDRESS")
    private String address;

    @Column(name = "FAVORITES_TEL")
    private String telNum;

    @Column(name = "FAVORITES_HISTORY")
    private String history;
}
