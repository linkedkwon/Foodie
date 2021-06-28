package kr.foodie.config.security.auth;

import kr.foodie.domain.user.RoleType;
import kr.foodie.domain.user.User;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public enum OAuthIdentifier {

    GOOGLE {
        @Override
        public User toEntity(Map<String, Object> attributes) {
            return buildMember((String)attributes.get("email"), (String)attributes.get("given_name"),
                    "google", (String)attributes.get("sub"));
        }
        @Override
        public String getEmail(Map<String, Object> attributes){
            return (String)attributes.get("email");
        }
    },
    KAKAO {
        @Override
        public User toEntity(Map<String, Object> attributes){
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

            return buildMember((String)kakaoAccount.get("email"), (String)profile.get("nickname"),
                        "kakao", Integer.toString((Integer) attributes.get("id")));
        }
        @Override
        public String getEmail(Map<String, Object> attributes) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

            return (String)kakaoAccount.get("email");
        }
    },
    NAVER {
        @Override
        public User toEntity(Map<String, Object> attributes){
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");

            return buildMember((String)response.get("email"), (String) response.get("name"),
                        "naver", (String) response.get("id"));
        }
        @Override
        public String getEmail(Map<String, Object> attributes){
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return (String)response.get("email");
        }
    };

    abstract public User toEntity(Map<String, Object> attributes);
    abstract public String getEmail(Map<String, Object> attributes);

    protected User buildMember(String email, String name, String provider, String providerId){
        Date time = Calendar.getInstance().getTime();

        return User.builder()
                .name(name)
                .email(email)
                .password(" ")
                .address(" + + + ")
                .phoneNum("휴대 전화를 입력해주세요.")
                .emailReceivedType("0")
                .snsReceivedType("0")
                .point(0)
                .userType("0")
                .role(RoleType.GENERAL)
                .createdDate(time)
                .lastModifiedDate(time)
                .provider(provider)
                .providerId(providerId)
                .build();
    }
}
