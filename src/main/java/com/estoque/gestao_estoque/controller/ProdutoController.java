package com.estoque.gestao_estoque.controller;

import com.estoque.gestao_estoque.model.Produto;
import com.estoque.gestao_estoque.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> getAllProdutos() {
        return produtoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.findById(id);
        return produto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProduto(@RequestBody Produto produto) {
        if (produtoService.existsByCodigo(produto.getCodigo())) {
            return ResponseEntity.badRequest().body("Código do produto já existe");
        }

        if (produto.getPrecoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("Preço deve ser maior que zero");
        }

        if (produto.getQuantidadeEstoque() < 0) {
            return ResponseEntity.badRequest().body("Estoque não pode ser negativo");
        }

        return ResponseEntity.ok(produtoService.save(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetails) {
        Optional<Produto> produto = produtoService.findById(id);
        if (produto.isPresent()) {
            Produto produtoExistente = produto.get();

            if (!produtoExistente.getCodigo().equals(produtoDetails.getCodigo()) &&
                    produtoService.existsByCodigo(produtoDetails.getCodigo())) {
                return ResponseEntity.badRequest().body("Código do produto já existe");
            }

            if (produtoDetails.getPrecoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest().body("Preço deve ser maior que zero");
            }

            if (produtoDetails.getQuantidadeEstoque() < 0) {
                return ResponseEntity.badRequest().body("Estoque não pode ser negativo");
            }

            produtoExistente.setCodigo(produtoDetails.getCodigo());
            produtoExistente.setNome(produtoDetails.getNome());
            produtoExistente.setCategoria(produtoDetails.getCategoria());
            produtoExistente.setQuantidadeEstoque(produtoDetails.getQuantidadeEstoque());
            produtoExistente.setPrecoUnitario(produtoDetails.getPrecoUnitario());

            return ResponseEntity.ok(produtoService.save(produtoExistente));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        if (produtoService.findById(id).isPresent()) {
            produtoService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}