package com.example.polls.DTO;


import com.example.polls.model.MaintenanceEmployee;
import lombok.*;

import java.io.Serializable;

import static java.util.Optional.ofNullable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaintenanceEmployeeDTO implements Serializable {


  private Long id;
  private String name;
  private String surname;
  private String email;
  //private Long phoneNumber;
  private String username;
  private String password;
  private SupervisorDTO supervisor;


  public static MaintenanceEmployeeDTO ofEmployee(MaintenanceEmployee maintenanceEmployee) {
    return MaintenanceEmployeeDTO.builder()
      .id(maintenanceEmployee.getEmployeeId())
      .name(maintenanceEmployee.getName())
      .surname(maintenanceEmployee.getSurname())
      .email(maintenanceEmployee.getEmail())
 //     .phoneNumber(maintenanceEmployee.getPhoneNumber())
//      .supervisor(ofNullable(maintenanceEmployee.getSupervisor())
//        .map(SupervisorDTO::ofSupervisorBasic)
   //     .orElse(null))
      .username(maintenanceEmployee.getUsername())
      .password(maintenanceEmployee.getPassword())
//                .user(ofNullable(maintenanceEmployee.getUser())
//                        .map(UserDTO::ofUserBasic)
//                        .orElse(null))
      .build();

  }

  public static MaintenanceEmployeeDTO ofEmployeeBasic(MaintenanceEmployee maintenanceEmployee) {
    return MaintenanceEmployeeDTO.builder()
      .id(maintenanceEmployee.getEmployeeId())
      .name(maintenanceEmployee.getName())
      .surname(maintenanceEmployee.getSurname())
      .email(maintenanceEmployee.getEmail())
    //  .phoneNumber(maintenanceEmployee.getPhoneNumber())
//                .user(ofNullable(maintenanceEmployee.getUser())
//                        .map(UserDTO::ofUserBasic)
//                        .orElse(null))
      .build();

  }


}
