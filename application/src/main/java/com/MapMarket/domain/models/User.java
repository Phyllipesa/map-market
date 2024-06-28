package com.MapMarket.domain.models;

import com.MapMarket.infrastructure.adapters.output.persistence.entities.PermissionEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class User implements UserDetails, Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private String userName;
  private String fullName;
  private String password;
  private Boolean accountNonExpired;
  private Boolean accountNonLocked;
  private Boolean credentialsNonExpired;
  private Boolean enable;

  private List<PermissionEntity> permissions;

  public User() {
  }

  public List<String> getRoles() {
    List<String> roles = new ArrayList<>();
    for (PermissionEntity permission : permissions) {
      roles.add(permission.getDescription());
    }
    return roles;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.permissions.stream()
        .map(permission -> new SimpleGrantedAuthority("ROLE_" + permission.getDescription()))
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.enable;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getAccountNonExpired() {
    return accountNonExpired;
  }

  public void setAccountNonExpired(Boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  public Boolean getAccountNonLocked() {
    return accountNonLocked;
  }

  public void setAccountNonLocked(Boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  public Boolean getCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public List<PermissionEntity> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<PermissionEntity> permissions) {
    this.permissions = permissions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User user)) return false;
    return Objects.equals(id, user.id) && Objects.equals(userName, user.userName) && Objects.equals(fullName, user.fullName) && Objects.equals(password, user.password) && Objects.equals(accountNonExpired, user.accountNonExpired) && Objects.equals(accountNonLocked, user.accountNonLocked) && Objects.equals(credentialsNonExpired, user.credentialsNonExpired) && Objects.equals(enable, user.enable) && Objects.equals(permissions, user.permissions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userName, fullName, password, accountNonExpired, accountNonLocked, credentialsNonExpired, enable, permissions);
  }
}
