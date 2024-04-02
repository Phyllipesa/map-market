package com.MapMarket.infrastructure.adapters.output.persistence.repositories;

import com.MapMarket.infrastructure.adapters.output.persistence.entities.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
}
