package kr.foodie.service;

import kr.foodie.domain.member.Member;
import kr.foodie.domain.member.RoleType;
import kr.foodie.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;


@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //to be changing as Query
    public String validateEmail(String email) {
        if(memberRepository.findByEmail(email).isPresent())
            return "1";
        return "0";
    }

    public String inquiryId(String name, String phoneNum){
       Member entity = memberRepository.findByNameAndPhoneNum(name, phoneNum)
                        .orElseGet(() -> {
                            Member member = new Member();
                            member.setEmail("1");
                            return member;
                        });
       return entity.getEmail();
    }

    public String inquiryPw(String email, String phoneNum){
        Member entity = memberRepository.findByEmailAndPhoneNum(email, phoneNum)
                .orElseGet(() -> {
                    Member member = new Member();
                    member.setEmail("1");
                    return member;
                });

        return entity.getEmail();
    }

    public String save(Member member){
        if(member.getTelNum().length() == 0) member.setTelNum("없음");

        Date time = Calendar.getInstance().getTime();

        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRoleType(RoleType.GENERAL);
        member.setCreatedDate(time);
        member.setLastModifiedDate(time);

        memberRepository.save(member);

        return "signup_done";
    }
}
