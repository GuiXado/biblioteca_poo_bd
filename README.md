# biblioteca_poo_bd

## Sistema de Biblioteca

Projeto desenvolvido para a disciplina de Programação Orientada a Objetos e Banco de Dados.

O sistema tem como objetivo realizar o gerenciamento de uma biblioteca, permitindo o cadastro de livros e estudantes, o controle de empréstimos e devoluções, além da geração de relatórios gerenciais.

Entre as funcionalidades disponíveis estão:

* Cadastro, consulta, alteração e exclusão de livros;
* Cadastro, consulta, alteração e exclusão de estudantes;
* Registro de empréstimos e devoluções;
* Consulta de empréstimos ativos;
* Consulta de empréstimos atrasados;
* Consulta do histórico de empréstimos;
* Relatório de estudantes com mais empréstimos;
* Relatório de livros mais emprestados;
* Relatório de livros sem estoque.

Tecnologias utilizadas:

- Java 21 para desenvolvimento da aplicação;
- JavaFX para a interface gráfica;
- JDBC para comunicação com o banco de dados;
- SQL Server para persistência dos dados;
- Gradle para gerenciamento e compilação do projeto.


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

O sistema deve permitir visualizar os livros cuja quantidade disponível seja igual a zero.

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

## Casos de Teste

| ID   | Caso de Teste                             | Procedimento                                        | Resultado Esperado                                         | Resultado Obtido |
| ---- | ----------------------------------------- | --------------------------------------------------- | ---------------------------------------------------------- | ---------------- |
| CT01 | Cadastrar Livro                           | Informar os dados do livro e clicar em Salvar       | Livro cadastrado com sucesso                               | Sucesso          |
| CT02 | Alterar Livro                             | Selecionar um livro, alterar os dados e salvar      | Livro atualizado com sucesso                               | Sucesso          |
| CT03 | Excluir Livro                             | Selecionar um livro e clicar em Excluir             | Livro removido do cadastro                                 | Sucesso          |
| CT04 | Cadastrar Estudante                       | Informar os dados do estudante e clicar em Salvar   | Estudante cadastrado com sucesso                           | Sucesso          |
| CT05 | Alterar Estudante                         | Selecionar um estudante, alterar os dados e salvar  | Estudante atualizado com sucesso                           | Sucesso          |
| CT06 | Excluir Estudante                         | Selecionar um estudante e clicar em Excluir         | Estudante removido do cadastro                             | Sucesso          |
| CT07 | Registrar Empréstimo                      | Selecionar estudante e livro e registrar empréstimo | Empréstimo cadastrado com sucesso                          | Sucesso          |
| CT08 | Registrar Devolução                       | Selecionar empréstimo ativo e informar devolução    | Empréstimo finalizado com sucesso                          | Sucesso          |
| CT09 | Consultar Empréstimos Ativos              | Executar relatório de empréstimos ativos            | Exibir apenas empréstimos dentro do prazo e sem devolução  | Sucesso          |
| CT10 | Consultar Empréstimos Atrasados           | Executar relatório de empréstimos atrasados         | Exibir apenas empréstimos vencidos e não devolvidos        | Sucesso          |
| CT11 | Consultar Histórico de Empréstimos        | Executar relatório de histórico                     | Exibir empréstimos já devolvidos                           | Sucesso          |
| CT12 | Consultar Estudantes com Mais Empréstimos | Executar relatório correspondente                   | Exibir ranking de estudantes por quantidade de empréstimos | Sucesso          |
| CT13 | Consultar Livros Mais Emprestados         | Executar relatório correspondente                   | Exibir ranking de livros por quantidade de empréstimos     | Sucesso          |
| CT14 | Consultar Livros Sem Estoque              | Executar relatório correspondente                   | Exibir apenas livros com quantidade igual a zero           | Sucesso          |



## Instruções de Execução

1. Executar o script `database.sql` para criação do banco de dados e das tabelas.
2. Verificar os parâmetros de conexão com o SQL Server configurados no sistema.
3. Executar a aplicação através do comando:

gradle run

### Observações

O projeto foi desenvolvido utilizando Java 21, JavaFX, Gradle e SQL Server.

O arquivo `app.jar` encontra-se disponível na pasta `app/build/libs`.

Para correta execução do sistema, é necessário possuir ambiente Java compatível e acesso ao banco de dados SQL Server configurado.
