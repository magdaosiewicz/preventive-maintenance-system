
package com.example.polls.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
//@Entity
//@Table(name= "USERS")
public class Employee {
//
//  @Id
//  @GeneratedValue//(strategy = GenerationType.IDENTITY)
//  @Column(name = "USER_ID")
//  public Long id;

  @NotBlank
  @Column(name = "NAME")
  private String name;

  @NotBlank
  @Column(name = "SURNAME", nullable = false)
  private String surname;

  @NaturalId
  @NotBlank
//  @Size(max = 40)
//  @Email
  @Column(name = "EMAIL", nullable = false)
  private String email;

//  @NotBlank
//  @Column(name = "PHONE_NUMBER", nullable = false)
//  private Long phoneNumber;

  @NotBlank
//  @Size(max = 15)
  private String username;

  @NotBlank
  @Size(max = 100)
  private String password;

//  @Enumerated(EnumType.STRING)
//  @NaturalId
//  @Column(length = 60)
//  private Role role;


//  @NotBlank
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "USER_ID"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
 // private Set<Role> roles = new HashSet<>();
  private Role roles = new Role();


  public Employee(String name, String surname, String email, String username, String password, Role roles) {
    this.name = name;
    this.surname = surname;
    this.email = email;
   // this.phoneNumber = phoneNumber;
    this.username = username;
    this.password = password;
    this.roles=roles;

  }

  Employee() {
  }

}
