package kr.foodie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.foodie.VO.UserVO;
import kr.foodie.entity.UserEntity;
import kr.foodie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @PatchMapping("")
  public String patchUser(@RequestBody UserVO userVO) {
    userRepository.updateUser(userVO.getName(), userVO.getPoint(), userVO.getUserId());
    return "success";
  }
}
