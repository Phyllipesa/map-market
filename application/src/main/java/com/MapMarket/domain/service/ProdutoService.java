package com.MapMarket.domain.service;

import com.MapMarket.domain.exception.ProductCreationException;
import com.MapMarket.domain.exception.ResourceNotFoundException;
import com.MapMarket.domain.exception.constants.ProdutoConstant;
import com.MapMarket.domain.logic.ValidationProduct;
import com.MapMarket.domain.models.Produto;
import com.MapMarket.domain.ports.input.UseCase;
import com.MapMarket.domain.ports.output.OutputPort;

public class ProdutoService implements UseCase<Produto> {

  private final OutputPort<Produto> outputPort;
  private final ValidationProduct validationProduct;

  public ProdutoService(OutputPort<Produto> outputPort, ValidationProduct validationProduct) {
    this.outputPort = outputPort;
    this.validationProduct = validationProduct;
  }

//  @Override
//  public List<Produto> findAll() throws ResourceNotFoundException {
//    List<Produto> produtos = outputPort.findAll();
//    if (produtos.isEmpty()) {
//      throw new ResourceNotFoundException("Não foram encontrados produtos");
//    }
//    return produtos;
//  }

  @Override
  public Produto findById(Long id) {
    return outputPort.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(ProdutoConstant.PRODUCT_NOT_FOUND + id));
  }

  @Override
  public Produto create(Produto produto) {
    validationProduct.validate(produto);
    return outputPort.create(produto)
        .orElseThrow(() -> new ProductCreationException(ProdutoConstant.ERROR_CREATING_PRODUCT));
  }

  @Override
  public Produto update(Long id, Produto produto) {
    validationProduct.validate(produto);
    findById(id);
    return outputPort.update(id, produto)
        .orElseThrow(() -> new ResourceNotFoundException(ProdutoConstant.PRODUCT_NOT_FOUND + id));
  }
}
