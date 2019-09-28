package com.example.polls.config;

import com.example.polls.websocket.UserSignedIn;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.support.WebSocketHttpRequestHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSocket
@ComponentScan(basePackages = "com.example.polls")
public class WebMvcConfig implements WebMvcConfigurer {

  private final long MAX_AGE_SECS = 3600;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("*")
      .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
      .maxAge(MAX_AGE_SECS);
  }


  @Bean
  public SimpleUrlHandlerMapping handlerMapping() {

    Map<String, Object> urlMap = new HashMap<String, Object>();

    urlMap.put("/actions", new WebSocketHttpRequestHandler(orderWebSocketHandler()));

    SimpleUrlHandlerMapping hm = new SimpleUrlHandlerMapping();
    hm.setOrder(-1);
    hm.setUrlMap(urlMap);

    return hm;
  }

  @Bean
  public WebSocketHandler orderWebSocketHandler() {
    return new UserSignedIn();
  }



}
