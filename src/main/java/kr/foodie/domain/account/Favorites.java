package kr.foodie.domain.account;

import javax.persistence.*;

@Entity
@Table(name = "FAVORITES")
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //mapping to member
    //mapping to detail
}
