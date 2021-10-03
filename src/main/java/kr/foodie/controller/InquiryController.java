package kr.foodie.controller;

import kr.foodie.VO.InquiryVO;
import kr.foodie.entity.InquiryEntity;
import kr.foodie.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/inquiry")
public class InquiryController {
  private final InquiryRepository inquiryRepository;

  @GetMapping("/user/{id}")
  public List<InquiryEntity> findByUserId(@PathVariable int id) {
    return inquiryRepository.findByUserId(id);
  }

  @PostMapping("/create")
  public String createInquiry(@RequestBody InquiryVO inquiryVO) {
    inquiryRepository.save(InquiryEntity.builder()
            .userId(inquiryVO.getUserId())
            .userName(inquiryVO.getUserName())
            .title(inquiryVO.getTitle())
            .content(inquiryVO.getContent())
            .comment("")
            .createdAt(Calendar.getInstance().getTime())
            .modifiedAt(Calendar.getInstance().getTime()).build()
    );
    return "success";
  }
}
