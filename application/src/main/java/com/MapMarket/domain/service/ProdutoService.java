package com.MapMarket.domain.service;

import com.MapMarket.domain.ports.input.UseCase;
import com.MapMarket.domain.ports.output.OutputPort;
import com.MapMarket.domain.exception.ProdutoNotFoundException;
import com.MapMarket.domain.models.Produto;

import java.util.List;

public class ProdutoService implements UseCase<Produto> {

  private final OutputPort<Produto> outputPort;

  public ProdutoService(OutputPort<Produto> outputPort) {
    this.outputPort = outputPort;
  }

  @Override
  public List<Produto> findAll() throws ProdutoNotFoundException {
    List<Produto> produtos = outputPort.findAll();
    if (produtos.isEmpty()) {
      throw new ProdutoNotFoundException("Não foram encontrados produtos");
    }
    return produtos;
  }
}