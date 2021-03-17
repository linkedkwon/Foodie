package kr.foodie.service;

import kr.foodie.domain.member.Member;
import kr.foodie.domain.member.RoleType;
import kr.foodie.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String validate(String email) {
        if(memberRepository.findMemberByEmail(email).isPresent())
            return "1";
        return "0";
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
