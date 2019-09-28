package com.example.polls.controller;
import com.example.polls.model.MaintenanceEmployee;
import com.example.polls.model.Role;
import com.example.polls.model.RoleName;
import com.example.polls.model.Supervisor;
import com.example.polls.payload.ApiResponse;
import com.example.polls.payload.JwtAuthenticationResponse;
import com.example.polls.payload.LoginRequest;
import com.example.polls.payload.SignUpRequest;
import com.example.polls.repository.MaintenanceEmployeeRepository;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.SupervisorRepository;
import com.example.polls.security.JwtTokenProvider;
import com.example.polls.service.EmployeeService;
import com.example.polls.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtTokenProvider tokenProvider;

  @Autowired
  MaintenanceEmployeeRepository maintenanceEmployeeRepository;

  @Autowired
  SupervisorRepository supervisorRepository;

  @Autowired
  EmployeeService employeeService;

  @Autowired
  SupervisorService supervisorService;

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

    String userRole = "EMPLOYEE";
    String supervisorRole = "SUPERVISOR";
//     &&  maintenanceEmployeeRepository.existsByUsername(signUpRequest.getUsername()
    if (supervisorRepository.existsByUsername(signUpRequest.getUsername())) {
      return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
        HttpStatus.BAD_REQUEST);
    }

//     && maintenanceEmployeeRepository.existsByEmail(signUpRequest.getEmail()
    if (maintenanceEmployeeRepository.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
        HttpStatus.BAD_REQUEST);
    }

    ///////////////////////////////////////////////////////////// // Creating user's account

    if (signUpRequest.getRoleName().equals(userRole)) {
      MaintenanceEmployee maintenanceEmployeeToPersist = MaintenanceEmployee.builder()
        .name(signUpRequest.getName())
        .surname(signUpRequest.getSurname())
        //   .phoneNumber(signUpRequest.getPhoneNumber())
        .email(signUpRequest.getEmail())
        .username(signUpRequest.getUsername())
        .password(signUpRequest.getPassword())
        .build();

      maintenanceEmployeeToPersist.setPassword(passwordEncoder.encode(maintenanceEmployeeToPersist.getPassword()));
      Role employyeRole = roleRepository.findByName(RoleName.EMPLOYEE)
        .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
      maintenanceEmployeeToPersist.setRoles(employyeRole);

      MaintenanceEmployee persistedMaintenanceEmployee = employeeService.addEmployee(maintenanceEmployeeToPersist);
      URI location = ServletUriComponentsBuilder
        .fromCurrentContextPath().path("/api/users/{username}")
        .buildAndExpand(persistedMaintenanceEmployee.getUsername()).toUri();

      return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    if (signUpRequest.getRoleName().equals(supervisorRole)) {
      Supervisor supervisorToPersist = Supervisor.builder()
        .name(signUpRequest.getName())
        .surname(signUpRequest.getSurname())
        //   .phoneNumber(signUpRequest.getPhoneNumber())
        .email(signUpRequest.getEmail())
        .username(signUpRequest.getUsername())
        .password(signUpRequest.getPassword())
        .build();

      supervisorToPersist.setPassword(passwordEncoder.encode(supervisorToPersist.getPassword()));
      Role supervisorRol = roleRepository.findByName(RoleName.SUPERVISOR)
        .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
      //supervisorToPersist.setRoles(Collections.singleton(supervisorRol));
      supervisorToPersist.setRoles(supervisorRol);


      Supervisor persistedSupervisor = supervisorService.addSupervisor(supervisorToPersist);

      URI location = ServletUriComponentsBuilder
        .fromCurrentContextPath().path("/api/usersSupervisor/{username}")
        .buildAndExpand(persistedSupervisor.getUsername()).toUri();

      return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    return null;
  }



  @MessageMapping("/hello")
  @SendTo("/topic/hi")
  public String greeting() throws Exception {

   // simpMessageSendingOperations.convertAndSend("/topic/hi", "uuuuuuuuuuuuuuuuu");
    return "jnjnjnjn z auth controller greeting function ";
  }



  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginRequest.getUsernameOrEmail(),
        loginRequest.getPassword()
      )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
  }



}
