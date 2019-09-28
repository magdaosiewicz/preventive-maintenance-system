package com.example.polls.websocket;


import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionHandler {



  private final Map<String, WebSocketSession> userLoggedInSession = new ConcurrentHashMap<>();


  public void addNewUSer(String username, WebSocketSession webSocketSession) {
    userLoggedInSession.put(username, webSocketSession);
  }

  public WebSocketSession getSession(String username) {
    return userLoggedInSession.get(username);
  }

  public Map<String, WebSocketSession> getUserLoggedInSession() {
    return userLoggedInSession;
  }



}
