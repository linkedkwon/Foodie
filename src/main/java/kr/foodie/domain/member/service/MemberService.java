package kr.foodie.domain.member.service;

import kr.foodie.domain.member.model.Member;
import kr.foodie.domain.member.model.RoleType;
import kr.foodie.domain.member.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

        //password encoding
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        //set member type
        member.setMemberType("0");
        member.setRoleType(RoleType.GENERAL);

        memberRepository.save(member);

        return "signup_done";
    }
}
