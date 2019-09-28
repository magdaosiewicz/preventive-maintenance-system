package com.example.polls.publisher;

import com.example.polls.event.EmployeeEvent;
import com.example.polls.model.MaintenanceEmployee;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class EmployeePublisher implements ApplicationEventPublisherAware {

  private ApplicationEventPublisher employeePublisher;

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher employeePublisher) {
    this.employeePublisher=employeePublisher;
  }

  public void publishOrder(MaintenanceEmployee maintenanceEmployee){
    employeePublisher.publishEvent(new EmployeeEvent(this, maintenanceEmployee));

  }


}
