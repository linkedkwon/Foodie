package kr.foodie.domain.account.model;

import kr.foodie.domain.detail.model.Detail;
import kr.foodie.domain.member.model.Member;

import javax.persistence.*;

@Entity
@Table(name = "COMMENT")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column(name = "COMMENT_NAME")
    private String name;

    @Column(name = "COMMENT_TEXT")
    private String text;

    @Column(name = "COMMENT_SHOP")
    private String shop;

    @Column(name = "COMMENT_SCORE")
    private String score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DETAIL_ID")
    private Detail detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

}
