package com.example.polls.security;
import com.example.polls.model.MaintenanceEmployee;
import com.example.polls.model.Role;
import com.example.polls.model.RoleName;
import com.example.polls.model.Supervisor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

  private Long id;

  private String name;

  private String surname;

  private String username;

  private String email;

  private RoleName roleName;

  public RoleName getRoleName() {
    return roleName;
  }

  public void setRoleName(RoleName roleName) {
    this.roleName = roleName;
  }

  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;


  public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  public UserPrincipal(Long id, String name, String surname, String username, String email, String password, RoleName roleName, Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.username = username;
    this.email = email;
    this.roleName=roleName;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserPrincipal createM(MaintenanceEmployee employee) {
    List<GrantedAuthority> authorities =
      Collections.singletonList(new SimpleGrantedAuthority(employee.getRoles().getName().name()));

    return new UserPrincipal(
      employee.getEmployeeId(),
      employee.getName(),
      employee.getSurname(),
      employee.getUsername(),
      employee.getEmail(),
      employee.getPassword(),
      employee.getRoles().getName(),
      authorities
    );
  }


  public static UserPrincipal createS(Supervisor employee) {
    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(employee.getRoles().getName().name()));

    return new UserPrincipal(
      employee.getSupervisorId(),
      employee.getName(),
      employee.getSurname(),
      employee.getUsername(),
      employee.getEmail(),
      employee.getPassword(),
      employee.getRoles().getName(),
      authorities
    );
  }


  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserPrincipal that = (UserPrincipal) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }








}
