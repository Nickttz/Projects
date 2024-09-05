# Status: Em desenvolvimento (pode ter alguns bugs) ⚠️

### Este é um aplicativo para gerenciar uma biblioteca. Usei a IDE do Visual Studio Code com extensões Java.

> Interface gráfica: terminal.
Tive alguns problemas com JavaFX e Scene Builder, então deixei de lado. ❗

## Estrutura de pastas

O projeto contém duas pastas por padrão, onde:

- `src`: a pasta para manter as classes ou objetos
- `lib`: a pasta para manter as dependências

### Tecnologias utilizadas 🖥️
- Java 
- PostgreSQL (senha e usuário estão na classe Conexao)

## Funcionalidades 

- Sistema de login
- Cadastro de livros e usuários
- Adicionar ou remover clientes e livros
- Exibição do acervo de livros e cadastro de usuários
- Exclusão de login
- Emprestimo e devolução de livros

## Estrutura do Banco de dados

> O Banco de dados possui quatro tabelas, sendo elas: login, usuario, livro e emprestado.

### A tabela *login* possui:
- max_dias: Máximo de dias que a pessoa pode ficar com algum livro.
- max_livros: Máximo de livros que a pessoa pode ter no histórico de empréstimos.
- senha: Senha do login.
- usuario: Usuario do login (chave primária).

### A tabela *usuario* possui:
- nome
- sobrenome
- data_nacimento
- cpf
- endereco
- id_usuario: Código identificador do cliente
- id_login: Chave estrangeira para referenciar usuario da tabela login

### A tabela *livro* possui:
- titulo_livro
- categoria
- quantidade: Quantidade de copias que o livro possui
- emprestados: Quantidade de livros que foram emprestados
- id_livro: Código identificador do livro
- id_login: Chave estrangeira para referenciar usuario da tabela login

### A tabela *emprestado* possui: 
- titulo: Titulo do livro emprestado
- codigo: Titulo do codigo do livro emprestado
- id_usuario: Código identificador do usuario que pediu o empréstimo
- id_login: Chave estrangeira para referenciar usuario da tabela login
- categoria: Categoria do livro emprestado
- locacao: Data da locação do livro
- devolucao: Data da devolução do livro
