package com.example.polls.security;

import com.example.polls.model.MaintenanceEmployee;
import com.example.polls.model.Supervisor;
import com.example.polls.repository.MaintenanceEmployeeRepository;
import com.example.polls.repository.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {


  @Autowired
  MaintenanceEmployeeRepository maintenanceEmployeeRepository;


  @Autowired
  SupervisorRepository supervisorRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String usernameOrEmail) {

    MaintenanceEmployee maintenanceEmployee = maintenanceEmployeeRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    Supervisor supervisor = supervisorRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

    //    Let people login with either username or email
    if (maintenanceEmployee!=null) {
      return UserPrincipal.createM(maintenanceEmployee);
    } else if (supervisor != null) {
     return UserPrincipal.createS(supervisor);

    } else {
      throw new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail);
    }

  }


  // This method is used by JWTAuthenticationFilter
  @Transactional
  public UserDetails loadUserById(Long id) {    /////////////// trezba dodac supervosor tez i w principal chyba tez

//    MaintenanceEmployee maintenanceEmployee = maintenanceEmployeeRepository.findById(id);
    Supervisor supervisor = supervisorRepository.findBySupervisorId(id);
    MaintenanceEmployee employee = maintenanceEmployeeRepository.findByEmployeeId(id);

    if (employee!=null) {
      return UserPrincipal.createM(employee);
    } else if (supervisor != null) {
      return UserPrincipal.createS(supervisor);
    } else {
      throw new UsernameNotFoundException("User not found with iddddd : " + id);
    }
  }


}
