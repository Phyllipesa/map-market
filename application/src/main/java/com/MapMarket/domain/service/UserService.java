package com.MapMarket.domain.service;

import com.MapMarket.domain.exception.constants.Constant;
import com.MapMarket.domain.models.User;
import com.MapMarket.domain.ports.output.FindByUserNameOutputPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final FindByUserNameOutputPort<User> outputPort;

  public UserService(FindByUserNameOutputPort<User> outputPort) {
    this.outputPort = outputPort;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    return outputPort.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(Constant.USERNAME_NOT_FOUND + username));
  }
}
