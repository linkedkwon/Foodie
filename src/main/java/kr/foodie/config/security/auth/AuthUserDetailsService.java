package kr.foodie.config.security.auth;

import kr.foodie.domain.member.Member;
import kr.foodie.domain.member.RoleType;
import kr.foodie.repo.MemberRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

//composed service
@RequiredArgsConstructor
@Service
public class AuthUserDetailsService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member entity = memberRepository.findMemberByEmail(username)
                .orElseThrow(()->{
                    return new UsernameNotFoundException(username + "not found");
                });
        return new AuthUserDetails(entity);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService()
                .loadUser(userRequest);

        //google
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        Date time = Calendar.getInstance().getTime();

        Member entity = memberRepository.findMemberByEmail(email)
                .orElseGet(() -> {
                    Member member = Member.builder()
                            .email(email)
                            .name(name)
                            .memberType("0")
                            .role(RoleType.GENERAL)
                            .createdDate(time)
                            .lastModifiedDate(time)
                            .provider(provider)
                            .providerId(providerId)
                            .build();
                    memberRepository.save(member);
                    return member;
                });

        return new AuthUserDetails(entity, oAuth2User.getAttributes());
    }
}