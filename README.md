 🏪 Sistema de Gestão de Estoque e Caixa

Sistema completo para controle de estoque, vendas e gestão de usuários desenvolvido em Spring Boot (Backend) e Angular (Frontend).

 👨‍💻 Desenvolvedores

- Henrique Carvalho - Backend Spring Boot
- Breno Cardoso - Frontend Angular

 🚀 Tecnologias

 Backend
- Java 17** + Spring Boot 3.5.7
- Spring Security com autenticação
- Spring Data JPA + Hibernate
- H2 Database (desenvolvimento)
- Maven para gerenciamento de dependências

 Frontend  
- Angular com TypeScript
- PrimeNG para componentes UI
- RxJS para programação reativa
- CSS3 para estilização

 📋 Funcionalidades

 👥 Gestão de Usuários
- CRUD completo de usuários
- Dois perfis: ADMIN e OPERADOR
- Validação de senha (8 chars, 1 maiúscula, 1 número)
- E-mail único

 📦 Gestão de Estoque
- Cadastro e edição de produtos
- Controle de quantidade em estoque
- Movimentações (Entrada/Ajuste)
- Validação de preço > 0

 💰 Módulo de Vendas (Caixa)
- Registro de vendas com múltiplos itens
- Baixa automática de estoque
- Cálculo de subtotal, total e troco
- Validação de estoque suficiente

📊 Relatórios
- Consulta de vendas por período
- Total de vendas por período

🔐 Segurança

- ADMIN: Acesso completo (usuários, produtos, movimentações)
- OPERADOR: Apenas módulo de vendas
- Autenticação via e-mail e senha

🗃️ Modelo de Dados

🏪 Sistema de Gestão de Estoque e Caixa

Sistema completo para controle de estoque, vendas e gestão de usuários desenvolvido em Spring Boot (Backend) e Angular (Frontend).

👨‍💻 Desenvolvedores

Henrique Carvalho - Backend Spring Boot

Breno Cardoso - Frontend Angular

🚀 Tecnologias

Backend

Java 17 + Spring Boot 3.5.7

Spring Security com autenticação

Spring Data JPA + Hibernate

H2 Database (desenvolvimento)

Maven para gerenciamento de dependências

Frontend

Angular com TypeScript

PrimeNG para componentes UI

RxJS para programação reativa

CSS3 para estilização

📋 Funcionalidades

👥 Gestão de Usuários

CRUD completo de usuários

Dois perfis: ADMIN e OPERADOR

Validação de senha (8 chars, 1 maiúscula, 1 número)

E-mail único

📦 Gestão de Estoque

Cadastro e edição de produtos

Controle de quantidade em estoque

Movimentações (Entrada/Ajuste)

Validação de preço > 0

💰 Módulo de Vendas (Caixa)

Registro de vendas com múltiplos itens

Baixa automática de estoque

Cálculo de subtotal, total e troco

Validação de estoque suficiente

📊 Relatórios

Consulta de vendas por período

Total de vendas por período

🔐 Segurança

ADMIN: Acesso completo (usuários, produtos, movimentações)

OPERADOR: Apenas módulo de vendas

Autenticação via e-mail e senha

🗃️ Modelo de Dados

Usuario → Venda → ItemVenda
    ↓           ↙
Produto ← MovimentacaoEstoque

🛠️ Como Executar

Backend

Navegue até a pasta do backend:
cd backend

Execute a aplicação:
mvn spring-boot:run


Acesse:

API: http://localhost:8080

H2 Console: http://localhost:8080/h2-console

Frontend

Navegue até a pasta do frontend:
cd frontend

Instale as dependências:
npm install

Execute a aplicação:
ng serve

Acesse: http://localhost:4200

👤 Usuários de Teste

Admin: admin@estoque.com / Admin123

Operador: operador@estoque.com / Operador123

📚 Endpoints da API

Autenticação

POST /api/auth/login - Login de usuário

Usuários (ADMIN)

GET/POST/PUT/DELETE /api/usuarios

Produtos (ADMIN)

GET/POST/PUT/DELETE /api/produtos

Vendas (OPERADOR)

POST /api/vendas - Registrar venda

GET /api/vendas/periodo - Vendas por período

Movimentações (ADMIN)

POST /api/movimentacoes - Registrar movimentação

🎯 Estrutura do Projeto
estoque_caixa/
├── backend/          # Spring Boot (Henrique Carvalho)
│   ├── src/
│   ├── pom.xml
│   └── README.md
└── frontend/         # Angular (Breno Cardoso)
    ├── src/
    ├── package.json
    └── angular.json
