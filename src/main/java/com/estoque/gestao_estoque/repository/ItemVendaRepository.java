package com.estoque.gestao_estoque.repository;

import com.estoque.gestao_estoque.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    List<ItemVenda> findByVendaId(Long vendaId);
}