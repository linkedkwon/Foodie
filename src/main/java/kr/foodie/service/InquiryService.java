package kr.foodie.service;

import kr.foodie.domain.account.Inquiry;
import kr.foodie.repo.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public void createInquiry(int userId, String userName, String title, String content){
        inquiryRepository.save(Inquiry.builder()
                                .userId(userId)
                                .userName(userName)
                                .title(title)
                                .content(content)
                                .comment("")
                                .createdTime(Calendar.getInstance().getTime())
                                .modifiedTime(Calendar.getInstance().getTime()).build());
    }
}
