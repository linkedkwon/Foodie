package kr.foodie.service;

import kr.foodie.domain.user.RoleType;
import kr.foodie.domain.user.User;
import kr.foodie.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String validateEmail(String email) {
        if(userRepository.findByEmail(email).isPresent())
            return "1";
        return "0";
    }

    public String inquiryId(String name, String phoneNum){
       User entity = userRepository.findByNameAndPhoneNum(name, phoneNum)
                        .orElseGet(() -> {
                            User user = new User();
                            user.setEmail("1");
                            return user;
                        });
       return entity.getEmail();
    }

    public String inquiryPw(String email, String phoneNum){
        User entity = userRepository.findByEmailAndPhoneNum(email, phoneNum)
                .orElseGet(() -> {
                    User user = new User();
                    user.setEmail("1");
                    return user;
                });

        return entity.getEmail();
    }

    public String save(User user){
        if(user.getTelNum().length() == 0) user.setTelNum("없음");

        Date time = Calendar.getInstance().getTime();

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPoint(0);
        user.setRoleType(RoleType.GENERAL);
        user.setCreatedDate(time);
        user.setLastModifiedDate(time);

        userRepository.save(user);

        return "signup_done";
    }

    public void updatePassword(String email, String password){
        User entity = userRepository.findByEmail(email)
                .orElseThrow(()->{
                    return new UsernameNotFoundException(email + "not found");
                });
        entity.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(entity);
    }

    public void update(User user){
        User entity = userRepository.findByEmail(user.getEmail())
                .orElseThrow(()->{
                   return new UsernameNotFoundException("not found");
                });
        System.out.println(user.getName());
        entity.setName(user.getName());
        if(user.getPassword().length() > 0)
            entity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        entity.setAddress(user.getAddress());
        entity.setEmailReceivedType(user.getEmailReceivedType());
        entity.setSnsReceivedType(user.getSnsReceivedType());
        entity.setPhoneNum(user.getPhoneNum());
        entity.setTelNum(user.getTelNum());

        Date time = Calendar.getInstance().getTime();
        entity.setLastModifiedDate(time);

        userRepository.save(entity);
    }
}
