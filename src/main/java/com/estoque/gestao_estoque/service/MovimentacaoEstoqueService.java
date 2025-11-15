package com.estoque.gestao_estoque.service;

import com.estoque.gestao_estoque.model.*;
import com.estoque.gestao_estoque.repository.MovimentacaoEstoqueRepository;
import com.estoque.gestao_estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MovimentacaoEstoqueService {

    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public MovimentacaoEstoque registrarMovimentacao(MovimentacaoEstoque movimentacao) {
        Produto produto = movimentacao.getProduto();
        Integer quantidadeAtual = produto.getQuantidadeEstoque();
        Integer quantidadeMovimentacao = movimentacao.getQuantidade();

        if (movimentacao.getTipo() == TipoMovimentacao.ENTRADA) {
            produto.setQuantidadeEstoque(quantidadeAtual + quantidadeMovimentacao);
        } else if (movimentacao.getTipo() == TipoMovimentacao.AJUSTE) {
            produto.setQuantidadeEstoque(quantidadeAtual + quantidadeMovimentacao);
        }

        produtoRepository.save(produto);
        return movimentacaoRepository.save(movimentacao);
    }

    public List<MovimentacaoEstoque> findAll() {
        return movimentacaoRepository.findAll();
    }

    public List<MovimentacaoEstoque> findByProdutoId(Long produtoId) {
        return movimentacaoRepository.findByProdutoId(produtoId);
    }
}