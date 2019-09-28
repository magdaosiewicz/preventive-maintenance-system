package com.example.polls.service;


import com.example.polls.model.MaintenanceEmployee;
import com.example.polls.model.Supervisor;
import com.example.polls.repository.MaintenanceEmployeeRepository;
import com.example.polls.repository.SupervisorRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {


    @Autowired
    private MaintenanceEmployeeRepository maintenanceEmployeeRepository;
;
//    @Autowired
//    private EmployeePublisher employeePublisher;

    @Autowired
    private SupervisorService supervisorService;

//    @Autowired
//    private UserService userService;

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Getter
    private List<MaintenanceEmployee> maintenanceEmployees = new ArrayList<>();


    public MaintenanceEmployee getEmployee(Long employeeId) {
        return maintenanceEmployeeRepository.getOne(employeeId);
    }

    public List getAllEmployees() {
        return maintenanceEmployeeRepository.findAll();
    }

    public MaintenanceEmployee addEmployee(MaintenanceEmployee maintenanceEmployee) {
        this.maintenanceEmployees.add(maintenanceEmployee);
//        employeePublisher.publishOrder(maintenanceEmployee);
        return maintenanceEmployeeRepository.save(maintenanceEmployee);
    }

    public MaintenanceEmployee updateEmployee(MaintenanceEmployee maintenanceEmployee) {
        return maintenanceEmployeeRepository.save(maintenanceEmployee);
    }

//    public MaintenanceEmployee updateEmployeeSupervisor(Long id, String supervisorUsername) {
//        MaintenanceEmployee maintenanceEmployee = this.getEmployee(id);
//        Supervisor supervisor = supervisorRepository.findByUsername(supervisorUsername);
//        maintenanceEmployee.setSupervisor(supervisor);
//        return maintenanceEmployeeRepository.save(maintenanceEmployee);
//    }


//    public MaintenanceEmployee getEmployeeByUsername(String username) {
//        supervisorService.getSupervisors().addAll(supervisorRepository.findAll());
//        this.maintenanceEmployees.addAll(maintenanceEmployeeRepository.findAll());
//        employeePublisher.publishOrder(new EmployeeWrapper(maintenanceEmployeeRepository.findEmployeeByUser(userService.getUserByUsername(username)), maintenanceEmployees,
//                maintenanceEmployeeRepository.findEmployeeByUser(userService.getUserByUsername(username)).getSupervisor()));
//        return maintenanceEmployeeRepository.findEmployeeByUser(userService.getUserByUsername(username));
//    }
//
//    public MaintenanceEmployee getEmployeeByUsernameWithoutPublish(String username) {
//        this.maintenanceEmployees.addAll(maintenanceEmployeeRepository.findAll());
//        return maintenanceEmployeeRepository.findEmployeeByUser(userService.getUserByUsername(username));
//    }
}
