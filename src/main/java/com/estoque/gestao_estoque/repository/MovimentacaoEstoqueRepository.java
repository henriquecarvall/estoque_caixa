package com.estoque.gestao_estoque.repository;

import com.estoque.gestao_estoque.model.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {
    List<MovimentacaoEstoque> findByProdutoId(Long produtoId);
    List<MovimentacaoEstoque> findByTipo(String tipo);
}