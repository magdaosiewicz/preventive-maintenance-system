package com.example.polls.event;

import com.example.polls.model.MaintenanceEmployee;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class EmployeeEvent extends ApplicationEvent {

//
//  @Getter
//  private EmployeeWrapper employeeWrapper;

  @Getter
  private MaintenanceEmployee maintenanceEmployee;

  public EmployeeEvent(Object source, MaintenanceEmployee maintenanceEmployee) {
    super(source);
    this.maintenanceEmployee=maintenanceEmployee;
  }


}
