package com.app.volunteer.dto;
import java.util.List;

import com.app.volunteer.model.Users;
import lombok.Data;

@Data
public class UserResponseDTO {
  private Integer id;
  private String username;
  private String email;
  List<Users> appUserRoles;
}
