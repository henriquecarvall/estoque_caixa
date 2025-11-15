package com.estoque.gestao_estoque;

import com.estoque.gestao_estoque.model.Perfil;
import com.estoque.gestao_estoque.model.Produto;
import com.estoque.gestao_estoque.model.Usuario;
import com.estoque.gestao_estoque.repository.ProdutoRepository;
import com.estoque.gestao_estoque.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public void run(String... args) throws Exception {
        criarUsuarios();
        criarProdutos();
    }

    private void criarUsuarios() {
        if (usuarioRepository.count() == 0) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@estoque.com");
            admin.setSenha(encoder.encode("Admin123"));
            admin.setPerfil(Perfil.ADMIN);
            usuarioRepository.save(admin);

            Usuario operador = new Usuario();
            operador.setNome("Operador");
            operador.setEmail("operador@estoque.com");
            operador.setSenha(encoder.encode("Operador123"));
            operador.setPerfil(Perfil.OPERADOR);
            usuarioRepository.save(operador);

            System.out.println("Usuários criados:");
            System.out.println("Admin: admin@estoque.com / Admin123");
            System.out.println("Operador: operador@estoque.com / Operador123");
        }
    }

    private void criarProdutos() {
        if (produtoRepository.count() == 0) {
            Produto produto1 = new Produto();
            produto1.setCodigo("P001");
            produto1.setNome("Notebook Dell");
            produto1.setCategoria("Informática");
            produto1.setQuantidadeEstoque(10);
            produto1.setPrecoUnitario(new BigDecimal("2500.00"));
            produtoRepository.save(produto1);

            Produto produto2 = new Produto();
            produto2.setCodigo("P002");
            produto2.setNome("Mouse Sem Fio");
            produto2.setCategoria("Informática");
            produto2.setQuantidadeEstoque(50);
            produto2.setPrecoUnitario(new BigDecimal("89.90"));
            produtoRepository.save(produto2);

            Produto produto3 = new Produto();
            produto3.setCodigo("P003");
            produto3.setNome("Teclado Mecânico");
            produto3.setCategoria("Informática");
            produto3.setQuantidadeEstoque(30);
            produto3.setPrecoUnitario(new BigDecimal("199.90"));
            produtoRepository.save(produto3);

            System.out.println("Produtos de teste criados!");
        }
    }
}