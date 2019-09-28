package com.example.polls.payload;
import com.example.polls.model.RoleName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeProfile {

  private Long id;
  private String username;
  private String name;
  private String surname;
  private String email;
  private RoleName roleName;

  public EmployeeProfile() {
  }

  public EmployeeProfile(Long id, String username, String name, String surname, String email, RoleName roleName) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.surname=surname;
    this.email=email;
    this.roleName=roleName;
  }


}
