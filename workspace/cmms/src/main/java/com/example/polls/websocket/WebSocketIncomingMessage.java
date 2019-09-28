package com.example.polls.websocket;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class WebSocketIncomingMessage {

  private String username;



}
