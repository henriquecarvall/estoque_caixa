package com.estoque.gestao_estoque.repository;

import com.estoque.gestao_estoque.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Venda> findByUsuarioId(Long usuarioId);

    @Query("SELECT SUM(v.valorTotal) FROM Venda v WHERE v.dataHora BETWEEN :inicio AND :fim")
    BigDecimal findTotalVendasPeriodo(LocalDateTime inicio, LocalDateTime fim);
}