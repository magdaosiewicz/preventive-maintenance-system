package com.example.polls.repository;


import com.example.polls.model.MaintenanceEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MaintenanceEmployeeRepository extends JpaRepository<MaintenanceEmployee, Long> {

//     MaintenanceEmployee findEmployeeByUser(User user);
//    List<MaintenanceEmployee> findEmployeesBySupervisor(Supervisor supervisor);


  MaintenanceEmployee findByEmployeeId(Long id);

  MaintenanceEmployee findByUsernameOrEmail(String username, String email);

  MaintenanceEmployee findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);


}
