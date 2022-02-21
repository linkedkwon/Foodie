package kr.foodie.config.security.auth;

import kr.foodie.domain.user.User;
import kr.foodie.repo.UserRepository;

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

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthUserDetailsService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User entity = userRepository.findByEmail(username)
                .orElseThrow(()->{
                    return new UsernameNotFoundException(username + "not found");
                });

        return new AuthUserDetails(updateVisitedCnt(entity));
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        Map<String, OAuthIdentifier> registration = initIdentifier();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        User entity = userRepository.findByEmail(registration.get(registrationId).getEmail(oAuth2User.getAttributes()))
                .orElseGet(() -> {
                    User user = registration.get(registrationId).toEntity(oAuth2User.getAttributes());
                    userRepository.save(user);
                    return user;
                });

        return new AuthUserDetails(updateVisitedCnt(entity));
    }

    public static final Map<String, OAuthIdentifier> initIdentifier() {
        Map<String, OAuthIdentifier> registration = new HashMap<String, OAuthIdentifier>();
        registration.put("google", OAuthIdentifier.GOOGLE);
        registration.put("kakao", OAuthIdentifier.KAKAO);
        registration.put("naver", OAuthIdentifier.NAVER);

        return registration;
    }

    private User updateVisitedCnt(User entity){
        entity.setVisitedCnt(entity.getVisitedCnt() + 1);
        userRepository.save(entity);

        return entity;
    }
}