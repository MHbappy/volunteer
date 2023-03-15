package com.app.volunteer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.app.volunteer.dto.UserDataDTO;
import com.app.volunteer.dto.UserResponseDTO;
import com.app.volunteer.model.Users;
import com.app.volunteer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/signin")
  public String login(
       @RequestParam String username,
       @RequestParam String password) {
    return userService.signin(username, password);
  }

  @PostMapping("/signup")
  public String signup(@RequestBody @Valid UserDataDTO user) {
    return userService.signupWithVolunteer(user);
  }

}
