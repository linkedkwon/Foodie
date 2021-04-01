package kr.foodie.config.security.auth;

import kr.foodie.domain.member.Member;
import kr.foodie.domain.member.RoleType;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public enum OAuthIdentifier {

    GOOGLE {
        @Override
        public Member toEntity(Map<String, Object> attributes) {
            return buildMember((String)attributes.get("email"), (String)attributes.get("name"),
                    "gogole", (String)attributes.get("sub"));
        }
        @Override
        public String getEmail(Map<String, Object> attributes){
            return (String)attributes.get("email");
        }
    },
    KAKAO {
        @Override
        public Member toEntity(Map<String, Object> attributes){
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
        public Member toEntity(Map<String, Object> attributes){
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

    abstract public Member toEntity(Map<String, Object> attributes);
    abstract public String getEmail(Map<String, Object> attributes);

    protected Member buildMember(String email, String name, String provider, String providerId){
        Date time = Calendar.getInstance().getTime();

        return Member.builder()
                .email(email)
                .name(name)
                .memberType("0")
                .role(RoleType.GENERAL)
                .createdDate(time)
                .lastModifiedDate(time)
                .provider(provider)
                .providerId(providerId)
                .build();
    }
}
