package com.example.polls.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class UserSignedIn extends TextWebSocketHandler {

  private WebSocketMessageConverter webSocketMessageConverter;


  private SessionHandler sessionHandler;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {}
  //bo to dziala w druga strone, tutaj oczekujesz na wiadoomsc z websocketu

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception
  {
    WebSocketIncomingMessage webSocketIncomingMessage = webSocketMessageConverter.convertToObject(message);
    sessionHandler.addNewUSer(webSocketIncomingMessage.getUsername(), session);

    System.out.println("Dodano mego zioma do mapy kurka " + webSocketIncomingMessage.getUsername());
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception
  {
    //    webSocketActionService.removeSession(session);
  }


}
