package kr.foodie.config.security.auth;

import kr.foodie.domain.member.Member;
import kr.foodie.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> entity = memberRepository.findMemberByEmail(username);
        if(entity.isPresent())
            return new PrincipalDetails(entity.get());
        return null;
    }
}
