package com.MapMarket.domain.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "estantes")
public class Estantes implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 10)
  private String nome;

  public Estantes() {
  }

  public Estantes(Long id, String nome) {
    this.id = id;
    this.nome = nome;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Estantes estantes)) return false;
    return Objects.equals(id, estantes.id) && Objects.equals(nome, estantes.nome);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nome);
  }
}
