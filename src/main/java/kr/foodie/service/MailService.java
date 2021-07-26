package kr.foodie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendCode(String email, String code) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setSubject("Foodie 인증코드");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setText("인증코드입니다.\n"+code);
        javaMailSender.send(message);
    }
}
