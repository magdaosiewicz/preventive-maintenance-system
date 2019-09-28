package com.example.polls.controller;

import com.example.polls.DTO.MaintenanceEmployeeDTO;
import com.example.polls.model.MaintenanceEmployee;
import com.example.polls.payload.EmployeeProfile;
import com.example.polls.payload.UserIdentityAvailability;
import com.example.polls.payload.UserSummary;
import com.example.polls.repository.MaintenanceEmployeeRepository;
import com.example.polls.security.CurrentUser;
import com.example.polls.security.UserPrincipal;
import com.example.polls.service.EmployeeService;
import com.example.polls.service.SupervisorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.polls.DTO.MaintenanceEmployeeDTO.ofEmployee;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    MaintenanceEmployeeRepository maintenanceEmployeeRepository;

    @Autowired
    private SupervisorService supervisorService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping("/auth/user/me")
    // @PreAuthorize("hasRole('EMPLOYEE')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(), currentUser.getSurname(), currentUser.getEmail(), currentUser.getRoleName());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !maintenanceEmployeeRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !maintenanceEmployeeRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping(value = "/allEmployees")
    public List getAllEmployees() {
        try {
            return employeeService.getAllEmployees();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @PostMapping(value = "/addEmployee")
    public ResponseEntity<MaintenanceEmployeeDTO> addEmployee(@RequestParam(value = "name") String name, @RequestParam(value = "surname") String surname, @RequestParam(value = "email") String email) {
        //  public void addEmployee(MaintenanceEmployeeDTO employee) throws URISyntaxException {

        MaintenanceEmployee maintenanceEmployeeToPersist = MaintenanceEmployee.builder()
                .name(name)
                .surname(surname)
                .email(email)
                //.phoneNumber(phoneNumber)
                //    .user(userService.getUserByUsername(name+surname))
                .build();
        MaintenanceEmployee persistedMaintenanceEmployee = employeeService.addEmployee(maintenanceEmployeeToPersist);
        return ResponseEntity.ok(ofEmployee(persistedMaintenanceEmployee));
    }

    @GetMapping("/users/{username}")
    public EmployeeProfile getEmployeeProfile(@PathVariable(value = "username") String username) {
        MaintenanceEmployee maintenanceEmployee = maintenanceEmployeeRepository.findByUsername(username);
        //   .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        if (maintenanceEmployee == null) {
            return new EmployeeProfile();
        }

        // simpMessageSendingOperations.convertAndSendToUser("monika", "magdusia", "uuuuuuu");
        // System.out.println("tak");

        return new EmployeeProfile(maintenanceEmployee.getEmployeeId(), maintenanceEmployee.getUsername(), maintenanceEmployee.getName(),
                maintenanceEmployee.getSurname(), maintenanceEmployee.getEmail(), maintenanceEmployee.getRoles().getName());
    }

    @GetMapping("/auth/userR/{username}")
    public Object getEmployeeRole(@PathVariable(value = "username") String username) {
        MaintenanceEmployee maintenanceEmployee = maintenanceEmployeeRepository.findByUsername(username);
        //.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

      //  simpMessageSendingOperations.convertAndSend("hbwudhywudh");
        simpMessagingTemplate.convertAndSendToUser("kupa", "/queue/notifications","kokokokok");
      //  simpMessageSendingOperations.convertAndSendToUser("kupa", "/queue/notifications","Srutututu");
        return "EMPLOYEE";
    }


}
