package com.app.volunteer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserDataDTO {

  @Size(min = 4, message = "This field length minimum 4")
  private String email;

  @Size(min = 4, message = "This field length minimum 4")
  private String password;

  @Size(min = 4, message = "This field length minimum 4")
  private String name;

  @Size(min = 4, message = "This field length minimum 4")
  private String fathersName;

  @Size(min = 4, message = "This field length minimum 4")
  private String mothersName;

  @Size(min = 4, message = "This field length minimum 4")
  private String address;

  @Size(min = 4, message = "This field length minimum 4")
  private String description;
}
