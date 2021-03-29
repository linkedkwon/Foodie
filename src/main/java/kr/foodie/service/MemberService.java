package kr.foodie.service;

import kr.foodie.domain.member.Member;
import kr.foodie.domain.member.RoleType;
import kr.foodie.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        member.setPoint("0");
        member.setRoleType(RoleType.GENERAL);
        member.setCreatedDate(time);
        member.setLastModifiedDate(time);

        memberRepository.save(member);

        return "signup_done";
    }

    public void updatePassword(String email, String password){
        Member entity = memberRepository.findByEmail(email)
                .orElseThrow(()->{
                    return new UsernameNotFoundException(email + "not found");
                });
        entity.setPassword(bCryptPasswordEncoder.encode(password));
        memberRepository.save(entity);
    }

    public void update(Member member){
        Member entity = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(()->{
                   return new UsernameNotFoundException("not found");
                });
        System.out.println(member.getName());
        entity.setName(member.getName());
        if(member.getPassword().length() > 0)
            entity.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        entity.setAddress(member.getAddress());
        entity.setEmailReceivedType(member.getEmailReceivedType());
        entity.setSnsReceivedType(member.getSnsReceivedType());
        entity.setPhoneNum(member.getPhoneNum());
        entity.setTelNum(member.getTelNum());

        Date time = Calendar.getInstance().getTime();
        entity.setLastModifiedDate(time);

        memberRepository.save(entity);
    }
}
