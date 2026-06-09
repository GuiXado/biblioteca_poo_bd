# biblioteca_poo_bd

<img width="1312" height="367" alt="trabalho BD" src="https://github.com/user-attachments/assets/185c5996-a2be-46cf-ba72-67a22be7de97" />

## Requisitos Funcionais

RF01 – Gerenciar Livros

O sistema deve permitir cadastrar, consultar, alterar e remover livros, contendo título, autor, editora, data de publicação e quantidade disponível.

RF02 – Gerenciar Estudantes

O sistema deve permitir cadastrar, consultar, alterar e remover estudantes, contendo nome, curso e telefone.

RF03 – Gerenciar Empréstimos

O sistema deve permitir registrar empréstimos de livros para estudantes, consultar empréstimos cadastrados, alterar informações do empréstimo e registrar devoluções ou cancelamentos.

RF04 – Consultar Empréstimos Ativos

O sistema deve permitir visualizar os empréstimos que ainda não possuem data de devolução e que se encontram dentro do prazo previsto.

RF05 – Consultar Empréstimos Atrasados

O sistema deve permitir visualizar os empréstimos cuja data prevista de devolução já expirou e que ainda não foram devolvidos.

RF06 – Consultar Histórico de Empréstimos

O sistema deve permitir visualizar os empréstimos já finalizados por devolução.

RF07 – Consultar Ranking de Estudantes

O sistema deve permitir visualizar os estudantes com maior quantidade de empréstimos realizados.

RF08 – Consultar Ranking de Livros

O sistema deve permitir visualizar os livros mais emprestados.

RF09 – Consultar Livros Sem Estoque

## Requisitos Não Funcionais

RNF01

O sistema deverá utilizar o banco de dados SQL Server para armazenamento das informações.

RNF02

O sistema deverá garantir a integridade referencial dos dados por meio de chaves primárias e estrangeiras.

RNF03

O sistema deverá persistir os dados mesmo após o encerramento da aplicação.

RNF04

O sistema deverá apresentar interface gráfica desenvolvida em JavaFX.

RNF05

O sistema deverá exibir mensagens de erro e validação quando operações inválidas forem realizadas.

RNF06

O sistema deverá permitir a geração de relatórios para apoio ao gerenciamento da biblioteca.


O sistema deve permitir visualizar os livros cuja quantidade disponível seja igual a zero.
