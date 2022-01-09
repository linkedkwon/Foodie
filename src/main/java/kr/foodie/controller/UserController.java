package kr.foodie.controller;

import kr.foodie.VO.UserVO;
import kr.foodie.entity.UserEntity;
import kr.foodie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/user")
public class UserController {
  private final UserRepository userRepository;

  @GetMapping("/userId/{id}")
  public UserEntity findByUserId(@PathVariable int id) {
    return userRepository.findByUserId(id);
  }
}
