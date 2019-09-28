package com.example.polls.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
public class EmployeeSignedInHandler {

  @Autowired
  private SimpMessageSendingOperations simpMessageSendingOperations;

  @EventListener(ApplicationReadyEvent.class)
  public void onEmployeeEvent() {
    simpMessageSendingOperations.convertAndSendToUser("monika", "magdusia", "uuuuuuu");
    System.out.println("tak");
  }

//  @Autowired
//  private SessionHandler sessionHandler;
//
//  @Autowired
//  private WebSocketMessageConverter webSocketMessageConverter;

//  @Override
//  public void onApplicationEvent(EmployeeEvent employeeEvent) {
//
//        Map<String, WebSocketSession> sessions = sessionHandler.getUserLoggedInSession();
//  //  EmployeeWrapper employeeWrapper = employeeEvent.getEmployeeWrapper();
//    //   MaintenanceEmployee maintenanceEmployeeeee =  employeeWrapper.getMaintenanceEmployeeSignedIn();
//  //  WebSocketSession webSocketSessionEmployee = sessionHandler.getSession(employeeWrapper.getMaintenanceEmployeeSignedIn().getUser().getUsername());
//
//        String wiadomosc = "EmployeeSignedInHandler";
//        sessions.entrySet().forEach(s -> {
//            if (!s.getKey().equals(employeeEvent.getMaintenanceEmployee().getUsername())) {
//                try {
//                    s.getValue().sendMessage(new TextMessage((wiadomosc)));
//
//                } catch (Exception e) {
//                }
//            }
//        });
//
//


//    try {
//      webSocketSessionEmployee.sendMessage(new TextMessage(webSocketMessageConverter.convertToJson(employeeWrapper)));
//
//    } catch (IOException e) {
//      e.printStackTrace();
//    }


}


//}
