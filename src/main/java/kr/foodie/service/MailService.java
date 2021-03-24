package kr.foodie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Random;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendCode(String email) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();

        String code = getRandomCode(email);

        message.setSubject("Foodie 인증코드");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setText("인증코드입니다.\n"+code);
        javaMailSender.send(message);
    }

    public String getRandomCode(String email) {
        int left = 65, right = 90, len = 6;
        return  new Random().ints(left, right + 1)
                .limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
