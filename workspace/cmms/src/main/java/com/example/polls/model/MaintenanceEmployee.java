package com.example.polls.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;
@Entity
@Getter
@Setter
@Table(name = "MAINTENANCE_TECHNICIANS", uniqueConstraints = {
  @UniqueConstraint(columnNames = {
    "username"
  }),
  @UniqueConstraint(columnNames = {
    "email"
  })
})
public class MaintenanceEmployee extends Employee {


  @Id
  @GeneratedValue
  @Column(name = "U_ID")
  public Long employeeId;


  @JsonIgnore
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "SUPERVISOR_ID")
  private Supervisor supervisor;

  public MaintenanceEmployee(@NotBlank String name, @NotBlank String surname, @NotBlank String email, @NotBlank @Size(max = 15) String username, @NotBlank @Size(max = 100) String password, Set<Role> roles, Supervisor supervisor) {
//    this.name = name;
//    this.surname = surname;
//    this.email = email;
//    this.username = username;
//    this.password = password;
//    this.roles = roles;
    //this.supervisor = supervisor;
  }

  @Builder
  public MaintenanceEmployee(String name, String surname, String email, String username, String password, Supervisor supervisor,  Role roles) {
    super(name, surname, email, username, password, roles);
    this.supervisor=supervisor;
  }

  public MaintenanceEmployee() {
  }


}
