package com.estoque.gestao_estoque.controller;

import com.estoque.gestao_estoque.model.MovimentacaoEstoque;
import com.estoque.gestao_estoque.model.TipoMovimentacao;
import com.estoque.gestao_estoque.model.Produto;
import com.estoque.gestao_estoque.service.MovimentacaoEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoEstoqueController {

    @Autowired
    private MovimentacaoEstoqueService movimentacaoService;

    @GetMapping
    public List<MovimentacaoEstoque> getAllMovimentacoes() {
        return movimentacaoService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createMovimentacao(@RequestBody MovimentacaoEstoque movimentacao) {
        if (movimentacao.getTipo() == TipoMovimentacao.AJUSTE) {
            Produto produto = movimentacao.getProduto();
            int estoqueFinal = produto.getQuantidadeEstoque() + movimentacao.getQuantidade();

            if (estoqueFinal < 0) {
                return ResponseEntity.badRequest().body("Ajuste resultaria em estoque negativo");
            }
        }

        try {
            MovimentacaoEstoque movimentacaoRegistrada = movimentacaoService.registrarMovimentacao(movimentacao);
            return ResponseEntity.ok(movimentacaoRegistrada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/produto/{produtoId}")
    public List<MovimentacaoEstoque> getMovimentacoesByProduto(@PathVariable Long produtoId) {
        return movimentacaoService.findByProdutoId(produtoId);
    }
}