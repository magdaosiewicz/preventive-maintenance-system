package com.example.polls.websocket;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

import java.io.IOException;

@Component
public class WebSocketMessageConverter {

  @Autowired
  private ObjectMapper webSocketJacksonObjectMapper;

  public WebSocketIncomingMessage convertToObject(TextMessage message) {
    try {
      return webSocketJacksonObjectMapper.readValue(message.getPayload(), WebSocketIncomingMessage.class);
    } catch (NullPointerException e) {
      System.out.print("Received null as TextMessage");
    } catch (IOException e) {
      System.out.print("Impossible to convert JSONObject to java object");
    }
    return null;
  }

  public String convertToJson(WebSocketMessage message) {
    try {
      return webSocketJacksonObjectMapper.writeValueAsString(message);
    } catch (JsonProcessingException e) {
      System.out.println("Impossible to convert to JSON");
    }
    return null;
  }


}
