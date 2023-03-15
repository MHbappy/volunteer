package com.app.volunteer.service;


import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.app.volunteer.config.Constraints;
import com.app.volunteer.dto.UserDataDTO;
import com.app.volunteer.dto.account.VolunteerInfo;
import com.app.volunteer.exception.CustomException;
import com.app.volunteer.model.UserRole;
import com.app.volunteer.model.Users;
import com.app.volunteer.model.Volunteer;
import com.app.volunteer.repository.UserRepository;
import com.app.volunteer.repository.VolunteerRepository;
import com.app.volunteer.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;


@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;
  private final VolunteerRepository volunteerRepository;
  private final ModelMapper modelMapper;
  private final RestTemplate restTemplate;

  public String signin(String email, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

      Users appUser = userRepository.findByEmail(email);

      return jwtTokenProvider.createToken(email, appUser);
    } catch (AuthenticationException e) {
      e.printStackTrace();
      throw new CustomException("Invalid email/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public String signup(Users appUser) {
    if (!userRepository.existsByEmail(appUser.getEmail())) {
      appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
      Users users = userRepository.save(appUser);
      return jwtTokenProvider.createToken(appUser.getEmail(), appUser);
    } else {
      throw new CustomException("Email is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  @Transactional
  public String signupWithVolunteer(UserDataDTO dataDTO) {
    Users appUser =  modelMapper.map(dataDTO, Users.class);

    if (!userRepository.existsByEmail(appUser.getEmail())) {
      appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
      appUser.setAppUserRoles(new ArrayList<>(Arrays.asList(UserRole.VOLUNTEER)));
      Users users = userRepository.save(appUser);

      Volunteer volunteer = new Volunteer();
      volunteer.setAddress(dataDTO.getAddress());
      volunteer.setName(dataDTO.getName());
      volunteer.setIsActive(true);
      volunteer.setFathersName(dataDTO.getFathersName());
      volunteer.setMothersName(dataDTO.getMothersName());
      volunteer.setDescription(dataDTO.getDescription());
      volunteer.setUsers(users);
      Volunteer volunteer1 = volunteerRepository.save(volunteer);

      HttpHeaders headers = new HttpHeaders();
      VolunteerInfo volunteerInfo = new VolunteerInfo();
      volunteerInfo.setVolunteerId(volunteer1.getId());

      Boolean acCreatedAccount = restTemplate.postForObject(Constraints.ACCOUNT_URL + "/api/account/register/" + volunteer1.getId(), null, Boolean.class);
      Boolean acCreatedLibrary = restTemplate.postForObject(Constraints.LIBRARY_URL + "/api/library/register/" + volunteer1.getId(), null, Boolean.class);

      return jwtTokenProvider.createToken(appUser.getEmail(), appUser);
    } else {
      throw new CustomException("Email is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public void delete(String email) {
    userRepository.deleteByEmail(email);
  }

  public Users search(String email) {
    Users appUser = userRepository.findByEmail(email);
    if (appUser == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
    return appUser;
  }


  public Users searchWithOutException(String email) {
    Users appUser = userRepository.findByEmail(email);
    return appUser;
  }

  public Users whoami(HttpServletRequest req) {
    return userRepository.findByEmail(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
  }

  public String refresh(String email) {
    return jwtTokenProvider.createToken(email, userRepository.findByEmail(email));
  }

}
