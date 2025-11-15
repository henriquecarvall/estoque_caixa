package com.estoque.gestao_estoque.service;

import com.estoque.gestao_estoque.model.Produto;
import com.estoque.gestao_estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void deleteById(Long id) {
        produtoRepository.deleteById(id);
    }

    public boolean existsByCodigo(String codigo) {
        return produtoRepository.existsByCodigo(codigo);
    }

    public Optional<Produto> findByCodigo(String codigo) {
        return produtoRepository.findByCodigo(codigo);
    }
}