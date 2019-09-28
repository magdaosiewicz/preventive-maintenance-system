package com.example.polls.DTO;

import com.example.polls.model.Supervisor;
import lombok.*;

import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupervisorDTO {

  private Long id;
  private String name;
  private String surname;
  private String email;
//  private Long phoneNumber;
  private String username;
  private String password;
  private List<MaintenanceEmployeeDTO> employees;


  public static SupervisorDTO ofSupervisor(Supervisor supervisor) {
    return SupervisorDTO.builder()
      .id(supervisor.getSupervisorId())
      .name(supervisor.getName())
      .surname(supervisor.getSurname())
      .email(supervisor.getEmail())
 //     .phoneNumber(supervisor.getPhoneNumber())
      .employees(ofNullable(supervisor.getMaintenanceEmployees())
        .map(employees -> employees
          .stream()
          .map(MaintenanceEmployeeDTO::ofEmployeeBasic)
          .collect(toList()))
        .orElse(null))
      .username(supervisor.getUsername())
      .password(supervisor.getPassword())
      .build();
  }


  public static SupervisorDTO ofSupervisorBasic(Supervisor supervisor) {
    return SupervisorDTO.builder()
      .id(supervisor.getSupervisorId())
      .name(supervisor.getName())
      .surname(supervisor.getSurname())
      .email(supervisor.getEmail())
 //     .phoneNumber(supervisor.getPhoneNumber())
      .username(supervisor.getUsername())
      .password(supervisor.getPassword())
      .build();
  }

}

