package com.example.polls.controller;
import com.example.polls.DTO.SupervisorDTO;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.MaintenanceEmployee;
import com.example.polls.model.Supervisor;
import com.example.polls.payload.EmployeeProfile;
import com.example.polls.payload.UserSummary;
import com.example.polls.repository.SupervisorRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.EmployeeService;
import com.example.polls.service.SupervisorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.polls.DTO.SupervisorDTO.ofSupervisor;
@RestController
@RequestMapping("/api")
public class SupervisorController {

  @Autowired
  private SupervisorService supervisorService;

  @Autowired
  SupervisorRepository supervisorRepository;

  @Autowired
  private EmployeeService employeeService;


  @Autowired
  AuthenticationManager authenticationManager;

  @GetMapping(value = "/allSupervisors")
  public List getAllSupervisors() {
    try {
      return supervisorService.getAllSupervisors();
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }
  private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);



  @GetMapping("/user/meS")
  // @PreAuthorize("hasRole('EMPLOYEE')")
  public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
    UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(), currentUser.getSurname(), currentUser.getEmail(), currentUser.getRoleName());
    return userSummary;
  }

  @PostMapping
  ResponseEntity<SupervisorDTO> addSupervisor(@RequestParam(value = "name") String name, @RequestParam(value = "surname") String surname, @RequestParam(value = "email") String email) {

    Supervisor supervisorToPersist = Supervisor.builder()
      .name(name)
      .surname(surname)
      .email(email)
      //      .phoneNumber(phoneNumber)
      .build();

    Supervisor persistedSupervisor = supervisorService.addSupervisor(supervisorToPersist);
    return ResponseEntity.ok(ofSupervisor(persistedSupervisor));
  }


  @GetMapping("/auth/userSR/{username}")
  public String getSupervisorRole(@PathVariable(value = "username") String username) {
    Supervisor supervisor = supervisorRepository.findByUsername(username);
    //  .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

    return "SUPERVISOR";
  }

  @GetMapping("/auth/usersSupervisor/{username}")
  public EmployeeProfile getEmployeeProfileSupervisor(@PathVariable(value = "username") String username) {
    Supervisor supervisor = supervisorRepository.findByUsername(username);
    // .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

    if (supervisor == null) {
      return new EmployeeProfile();
    }

    return new EmployeeProfile(supervisor.getSupervisorId(), supervisor.getUsername(), supervisor.getName(),
      supervisor.getSurname(), supervisor.getEmail(), supervisor.getRoles().getName());
  }




}
