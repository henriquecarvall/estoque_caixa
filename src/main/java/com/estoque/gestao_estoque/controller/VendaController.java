package com.estoque.gestao_estoque.controller;

import com.estoque.gestao_estoque.model.Venda;
import com.estoque.gestao_estoque.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @GetMapping
    public List<Venda> getAllVendas() {
        return vendaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> getVendaById(@PathVariable Long id) {
        Optional<Venda> venda = vendaService.findById(id);
        return venda.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createVenda(@RequestBody Venda venda) {
        if (venda.getValorRecebido().compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.badRequest().body("Valor recebido não pode ser negativo");
        }

        try {
            Venda vendaRealizada = vendaService.realizarVenda(venda);
            return ResponseEntity.ok(vendaRealizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/periodo")
    public List<Venda> getVendasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return vendaService.findByPeriodo(inicio, fim);
    }

    @GetMapping("/relatorio/total")
    public ResponseEntity<?> getTotalVendasPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        try {
            BigDecimal total = vendaService.findTotalVendasPeriodo(inicio, fim);
            return ResponseEntity.ok().body(Map.of("total", total != null ? total : BigDecimal.ZERO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao calcular total de vendas");
        }
    }
}