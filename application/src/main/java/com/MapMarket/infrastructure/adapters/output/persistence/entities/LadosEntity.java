package com.MapMarket.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "lados")
public class LadosEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 1)
  private String lado;

  @ManyToOne
  @JoinColumn(name = "id_estante")
  private EstantesEntity estante;

  public LadosEntity(Long id, String lado, EstantesEntity estante) {
    this.id = id;
    this.lado = lado;
    this.estante = estante;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLado() {
    return lado;
  }

  public void setLado(String lado) {
    this.lado = lado;
  }

  public EstantesEntity getEstante() {
    return estante;
  }

  public void setEstante(EstantesEntity estante) {
    this.estante = estante;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof LadosEntity that)) return false;
    return Objects.equals(id, that.id) && Objects.equals(lado, that.lado) && Objects.equals(estante, that.estante);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, lado, estante);
  }
}
