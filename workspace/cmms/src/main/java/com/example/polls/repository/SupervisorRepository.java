package com.example.polls.repository;

import com.example.polls.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {


  //  Supervisor findByEmployeesId(Long id);  // to jest zle i z tego jednak nei korzystam
//    Supervisor findSupervisorByUser(User user);




  Optional<Supervisor> findByEmail(String email);

  Supervisor findByUsernameOrEmail(String username, String email);
  Supervisor findByUsername(String username);

//  List<Supervisor> findByIdIn(List<Long> userIds);

  Supervisor findBySupervisorId(Long id);



  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);



}
