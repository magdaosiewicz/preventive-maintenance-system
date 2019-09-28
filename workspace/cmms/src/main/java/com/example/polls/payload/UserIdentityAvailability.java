package com.example.polls.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserIdentityAvailability  {

  private Boolean available;

  public UserIdentityAvailability(Boolean available) {
    this.available = available;
  }

  public Boolean getAvailable() {
    return available;
  }

  public void setAvailable(Boolean available) {
    this.available = available;
  }


}
