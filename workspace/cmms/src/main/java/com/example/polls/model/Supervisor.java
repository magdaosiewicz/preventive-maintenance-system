package com.example.polls.model;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "SUPERVISORS", uniqueConstraints = {
  @UniqueConstraint(columnNames = {
    "username"
  }),
  @UniqueConstraint(columnNames = {
    "email"
  })
})
@NoArgsConstructor
public class Supervisor extends Employee{


  @Id
  @GeneratedValue //(strategy = GenerationType.IDENTITY)
  @Column(name = "U_ID")
  public Long supervisorId;


    @OneToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.ALL,
            CascadeType.MERGE
    })
    @JoinTable(name = "SUPERVISOR_EMPLOYEES",
            joinColumns = @JoinColumn(name = "SUPERVISOR_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID")
    )
    private List<MaintenanceEmployee> maintenanceEmployees = new ArrayList<>();


    @Builder
    public Supervisor(String name, String surname, String email,  String username, String password, List<MaintenanceEmployee> maintenanceEmployees,  Role roles) {
        super(name, surname, email, username, password,  roles);
        this.maintenanceEmployees = maintenanceEmployees;


    }


}
