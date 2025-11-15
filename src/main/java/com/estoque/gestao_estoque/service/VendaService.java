package com.estoque.gestao_estoque.service;

import com.estoque.gestao_estoque.model.*;
import com.estoque.gestao_estoque.repository.VendaRepository;
import com.estoque.gestao_estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Venda realizarVenda(Venda venda) {
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemVenda item : venda.getItens()) {
            Produto produto = item.getProduto();
            Optional<Produto> produtoOpt = produtoRepository.findById(produto.getId());

            if (produtoOpt.isEmpty()) {
                throw new RuntimeException("Produto não encontrado: " + produto.getId());
            }

            Produto produtoBD = produtoOpt.get();

            if (produtoBD.getQuantidadeEstoque() < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para: " + produtoBD.getNome() + ". Disponível: " + produtoBD.getQuantidadeEstoque());
            }

            if (item.getQuantidade() <= 0) {
                throw new RuntimeException("Quantidade deve ser maior que zero");
            }

            produtoBD.setQuantidadeEstoque(produtoBD.getQuantidadeEstoque() - item.getQuantidade());
            produtoRepository.save(produtoBD);

            item.setPrecoUnitario(produtoBD.getPrecoUnitario());
            item.setSubtotal(produtoBD.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())));
            item.setVenda(venda);

            valorTotal = valorTotal.add(item.getSubtotal());
        }

        venda.setValorTotal(valorTotal);

        if (venda.getValorRecebido().compareTo(valorTotal) < 0) {
            throw new RuntimeException("Valor recebido não pode ser menor que o total da venda");
        }

        venda.setTroco(venda.getValorRecebido().subtract(valorTotal));

        return vendaRepository.save(venda);
    }

    public List<Venda> findAll() {
        return vendaRepository.findAll();
    }

    public Optional<Venda> findById(Long id) {
        return vendaRepository.findById(id);
    }

    public List<Venda> findByPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return vendaRepository.findByDataHoraBetween(inicio, fim);
    }

    public BigDecimal findTotalVendasPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return vendaRepository.findTotalVendasPeriodo(inicio, fim);
    }
}