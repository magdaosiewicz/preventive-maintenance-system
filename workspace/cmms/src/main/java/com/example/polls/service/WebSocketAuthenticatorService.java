package com.example.polls.service;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class WebSocketAuthenticatorService  {

  // This method MUST return a UsernamePasswordAuthenticationToken instance, the spring security chain is testing it with 'instanceof' later on. So don't use a subclass of it or any other class
  public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String  username, final String password) throws AuthenticationException {
    if (username == null || username.trim().length()==0) {
      throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
    }
    if (password == null || password.trim().length()==0) {
      throw new AuthenticationCredentialsNotFoundException("Password was null or empty.");
    }
    // Add your own logic for retrieving user in fetchUserFromDb()
//    if (fetchUserFromDb(username, password) == null) {
//      throw new BadCredentialsException("Bad credentials for user " + username);
//    }

    // null credentials, we do not pass the password along
    return new UsernamePasswordAuthenticationToken(
      username,
      null,
      Collections.singleton((GrantedAuthority) () -> "EMPLOYEE") // MUST provide at least one role
    );
  }

}
