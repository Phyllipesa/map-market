package com.MapMarket.application.integrationTests.dto;

import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialsDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private String username;
  private String password;


  public AccountCredentialsDto() {}

  public AccountCredentialsDto(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AccountCredentialsDto that)) return false;
    return Objects.equals(username, that.username) && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }
}
