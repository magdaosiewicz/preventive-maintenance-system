package com.example.polls.service;


import com.example.polls.model.Supervisor;
import com.example.polls.repository.MaintenanceEmployeeRepository;
import com.example.polls.repository.SupervisorRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupervisorService {

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private MaintenanceEmployeeRepository maintenanceEmployeeRepository;

//    @Autowired
//    private SupervisorPublisher supervisorPublisher;

    @Autowired
    private EmployeeService employeeService;

    @Getter
    private List<Supervisor> supervisors = new ArrayList<>();

    public Supervisor addSupervisor(Supervisor supervisor) {
        this.supervisors.add(supervisor);
        return supervisorRepository.save(supervisor);
    }

    public Supervisor getSupervisorById(Long id) {
        return supervisorRepository.getOne(id);
    }

//    public Supervisor getSupervisorByUsername(String username) {
//        System.out.println("dzien dobry supervisor");
//        employeeService.getMaintenanceEmployees().addAll(maintenanceEmployeeRepository.findAll());
//        this.supervisors.addAll(supervisorRepository.findAll());
//        supervisorPublisher.publishOrder(new SupervisorWrapper(
//                supervisorRepository.findSupervisorByUser(userService.getUserByUsername(username)),
//                this.supervisors));
//        return supervisorRepository.findSupervisorByUser(userService.getUserByUsername(username));
//    }


//    public Supervisor getSupervisorByUsernameWithoutPublish(String username) {
//        System.out.println("dzien dobry supervisor");
//        employeeService.getMaintenanceEmployees().addAll(maintenanceEmployeeRepository.findAll());
//        this.supervisors.addAll(supervisorRepository.findAll());
////        supervisorPublisher.publishOrder(new SupervisorWrapper(
////                supervisorRepository.findSupervisorByUser(userService.getUserByUsername(username)),
////                this.supervisors));
//        return supervisorRepository.findSupervisorByUser(userService.getUserByUsername(username));
//    }


    public List<Supervisor> getAllSupervisors() {
        return supervisorRepository.findAll();
    }

    public Supervisor updateSupervisor(Supervisor supervisor) {
        return supervisorRepository.save(supervisor);
    }

//    public Supervisor getSupervisorByEmployeesUsername(String employeeUsername) {
//        for (int i = 0; i < supervisors.size(); i++) {
//            for (int j = 0; j < supervisors.get(i).getMaintenanceEmployees().size(); j++) {
//                if (supervisors.get(i).getMaintenanceEmployees().get(j).getUser().getUsername().equals(employeeUsername)) {
//
//                    this.supervisors.get(i).getMaintenanceEmployees().addAll(maintenanceEmployeeRepository.findEmployeesBySupervisor(supervisors.get(i)));
//                    return supervisors.get(i);
//                }
//            }
//        }
//        return null;
//    }

//    public Supervisor updateSupervisorUser(Long id, User user) {
//        Supervisor supervisor = getSupervisorById(id);
//        supervisor.setUser(user);
//        return supervisorRepository.save(supervisor);
//    }




//    public Supervisor getSeupervisorByEmployeeId(Long id) {
//        return supervisorRepository.findByEmployeesId(id);
//    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class NotFoundException extends RuntimeException {
        public static final String message = "Id not found";

        public NotFoundException() {
            super(message);
        }
    }
}
