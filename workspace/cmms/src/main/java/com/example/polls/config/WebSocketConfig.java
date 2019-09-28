package com.example.polls.config;

import com.example.polls.payload.JwtAuthenticationResponse;
import com.example.polls.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  public static final String[] BROKER_DESTINATION_PREFIXES() {
    return new String[]{"/queue/", "/topic/"};
  }

  public static final String[] APPLICATION_DESTINATION_PREFIXES() {
    return new String[]{"/app"};
  }

  public static final String[] ENDPOINTS() {
    return new String[]{"/ws/notifications"};
  }

  @Autowired
  private JwtTokenProvider tokenProvider;



  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(new AuthorizedChannelInterceptor(tokenProvider));
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker(WebSocketConfig.BROKER_DESTINATION_PREFIXES());
    registry.setApplicationDestinationPrefixes(WebSocketConfig.APPLICATION_DESTINATION_PREFIXES());
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry
            .addEndpoint(WebSocketConfig.ENDPOINTS())
            .setAllowedOrigins("*")
            .withSockJS();
  }

//slyszysz?

  public static class AuthorizedChannelInterceptor implements ChannelInterceptor {

    private JwtTokenProvider tokenProvider;
//    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private JwtAuthenticationResponse jwtAuthenticationResponse;

    public AuthorizedChannelInterceptor(JwtTokenProvider tokenProvider) {
      this.tokenProvider = tokenProvider;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
      StompHeaderAccessor accessor =
        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
      if (StompCommand.CONNECT.equals(accessor.getCommand())) {
        String token = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
       // if (!StringUtils.isEmpty(token)) {
        if (tokenProvider.validateToken(token)) {
       //   Authentication authentication =
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            SecurityContextHolder.getContext().setAuthentication(authentication);
          accessor.setUser(authentication);
        } else {
          throw new IllegalStateException("Only authenticated users can open websocket sessions");
        }

      }
      return message;
    }
  }
















}
