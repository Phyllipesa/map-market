package com.MapMarket.domain.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Permission implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private String description;

  public Permission() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Permission that)) return false;
    return Objects.equals(id, that.id) && Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description);
  }

}
