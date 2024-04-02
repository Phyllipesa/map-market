package com.MapMarket.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "produto")
public class ProdutoEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String nome;

  public ProdutoEntity() {
  }

  public ProdutoEntity(Long id, String nome) {
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
    if (!(o instanceof ProdutoEntity produto)) return false;
    return Objects.equals(id, produto.id) && Objects.equals(nome, produto.nome);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nome);
  }
}
