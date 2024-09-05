package Menu;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

import Biblioteca.Biblioteca;
import Conta.ContasSQL;
import Exceptions.LivroNaoCadastradoEx;
import Exceptions.NenhumaCopiaEmprestadaEx;
import Exceptions.UsuarioNaoCadastradoEx;
import Livro.Livro;
import Usuario.Usuário;
import Validacoes.ValidaLivroEx;
import Validacoes.ValidaUsuarioEx;

public class Menu {

    private Scanner ler;
    private Biblioteca bio;
    private int maxDias;
    private int maxNum;

    public Menu(Scanner ler, Biblioteca bio, int maxDias, int maxNum){
        this.ler = ler;
        this.bio = bio;
        this.maxDias = maxDias;
        this.maxNum = maxNum;
    }

    public void loginSistema() throws Exception {
        
        int opcao = -1;
        do{
            try{
                System.out.println("\nFaça um login ou cadastre sua nova conta!\n");
                System.out.println("[1] - Efetuar login.\n");
                System.out.println("[2] - Efetuar cadastro.\n");
                System.err.println("[0] - Sair.\n");
                System.out.print("Digite a opcao: ");
                opcao = ler.nextInt();
                System.out.println("");

                switch (opcao) {
                    case 1: {
                        System.out.print("Login: ");
                        String login = ler.next();
                        System.out.print("Senha: ");
                        String senha = ler.next();
                        System.out.println("");
                        
                        if(ValidaUsuarioEx.usuarioExiste(login)){
                            if(ValidaUsuarioEx.verificaSenha(login, senha)){
                                System.out.println("Login efetuado com sucesso!");
                                ContasSQL.setIDlogin(login);
                                IniciarMenu();
                                break;
                            }
                            else{
                                System.out.println("Senha incorreta!");
                            }
                        }
                        else {
                            System.out.println("\nNao foi possivel encontrar seu login.");
                        }
                    }
                    break;
                    
                    case 2: {
                        System.out.print("\nDigite um login: ");
                        String new_login = ler.next();
                        System.out.print("\nCrie uma senha: ");
                        String new_senha = ler.next();
                        System.out.println("\nAdicione os valores de acordo com a politica que você deseja: \n");
                        boolean inputValido = false;

                        do {
                            try {
                                System.out.println("Qual o numero maximo de livros que uma pessoa ter em sua lista de livros emprestados?: ");
                                maxNum = ler.nextInt();
                    
                                System.out.println("Qual o numero de dias de tolerancia que um usuario pode ter para a devolucao do livro?: ");
                                maxDias = ler.nextInt();
                    
                                if(maxNum <= 0 || maxDias < 0) {
                                    System.out.println("Valores invalidos. Tente novamente.");
                                    inputValido = false;
                                
                                } else {
                                    inputValido = true;
                                    ContasSQL.criarLogin(new_login, new_senha, maxNum, maxDias);
                                }

                            }catch (InputMismatchException e) {
                                System.out.println("Entrada invalida. Tente novamente.");
                                inputValido = false;
                                ler.nextLine();
                            }
                        
                        } while (inputValido == false);
                        break;
                    }
                    case 0: {
                        System.out.println("Encerrando programa...");
                        System.exit(0);
                    }
                }
            } catch(InputMismatchException e) {
                System.out.println("Por favor, insira um valor entre 0 e 2");
                ler.nextLine();
                opcao = -1;
            } 
        } while(opcao != 0);
    }

    public void IniciarMenu() throws Exception{

        int opcao = -1;
        do {
            try {

                System.out.println("\n================ MENU BIBLIOTECA ================\n");
                System.out.println("Escolha uma das opcoes abaixo:\n");
                System.out.println("[1] - Manuntecao.\n");
                System.out.println("[2] - Cadastro.\n");
                System.out.println("[3] - Emprestimo ou devolucao.\n");
                System.out.println("[4] - Relatorio.\n");
                System.out.println("[5] - Deletar conta\n");
                System.out.println("[0] - Sair da conta.\n");
                System.out.print("Digite a opcao: ");
                opcao = ler.nextInt();
                System.out.print("");

                switch(opcao){

                    case 1: {
                        manutenção();
                        break;
                    }
                    case 2: {
                        cadastro();
                        break;
                    }
                    case 3: {
                        emprestimo();
                        break;
                    }
                    case 4: {
                        relatorio();
                        break;
                    }
                    case 5: {
                        deletarConta();
                        break;
                    }

                    case 0: {
                        System.out.println("Saindo...");
                        loginSistema();
                        break;
                    }
                    default: {
                        System.out.println("Valor inválido!");
                        System.out.println("Por favor, Insira um valor entre 0 e 4.");
                        break;
                    }
                }

            }catch(InputMismatchException e){
                System.out.println("Valor inválido!");
                System.out.println("Por favor, escolha numero entre 0 e 4.");
                ler.nextLine();
                opcao = -1;
            }
        
        }while(opcao != 0);
    }

    public void manutenção() throws Exception {
        int opcao = -1;
    
        do {
            try {
                System.out.println("\n===== Manutencao =====\n");
                System.out.println("Escolha uma das opcoes abaixo: ");
                System.out.println("\n[1] - Apagar todos os registros de livros e/ou usuarios.");
                System.out.println("\n[2] - Deletar um livro ou um usuario especifico.");
                System.out.println("\n[0] - Voltar ao menu principal.\n");
                System.out.print("Digite a opcao: ");
                opcao = ler.nextInt();
                System.out.print("");
    
                switch (opcao) {
                    case 1:
                        int num = -1;

                        do {
                            try {
                                System.out.println("[1] - Apagar usuarios\n");
                                System.out.println("[2] - Apagar livros\n");
                                System.out.println("[0] - voltar\n");
                                System.out.print("Digite a opcao: ");
                                System.out.println("");
                                num = ler.nextInt();

                                switch(num) {
                                    
                                    case 1: {
                                        System.out.println("Voce realmente deseja apagar os dados de usuarios?\n");
                                        System.out.println("[1] - Sim\n");
                                        System.out.println("[2] - Nao\n");
                                        System.out.print("Digite a opcao: ");
                                        System.out.println("");
                                        int num2 = ler.nextInt();
                                        if(num2 == 1){
                                            bio.deletarUsuarios();
                                            num = 0;
                                            break;
                                        }
                                        else{
                                            num = 0;
                                            break;
                                        }
                                    }
                                    case 2: {
                                        System.out.println("Voce realmente deseja apagar os dados de livros?\n");
                                        System.out.println("[1] - Sim\n");
                                        System.out.println("[2] - Nao\n");
                                        System.out.print("Digite a opcao: ");
                                        System.out.println("");
                                        int num2 = ler.nextInt();
                                        if(num2 == 1){
                                            bio.deletarLivros();
                                            num = 0;
                                            break;
                                        }
                                        else{
                                            num = 0;
                                            break;
                                        }
                                    }
                                }
                            } catch(InputMismatchException e) {
                                    System.err.println("Por favor, digite um valor entre 0 e 2");
                            }
                        } while(num != 0);
                        break;
    
                    case 2: {
                        int escolha = -1; 
                        
                        do {
                            try{
                                System.out.println("\n[1] - Deletar usuario");
                                System.out.println("\n[2] - Deletar livro");
                                System.out.println("\n[0] - Voltar ao menu principal.");
                                System.out.print("Digite a opcao: ");
                                escolha = ler.nextInt();
                        
                                switch(escolha) {
                                    
                                    case 1: {
                                        System.out.println("\nDigite o codigo do usuario");
                                        System.out.print("codigo: ");
                                        int codigo = ler.nextInt();
                                        bio.deletarUsuario(bio.getUsuarioSQL(codigo));
                                        System.out.print("\n");
                                        System.out.println("Usuario deletado com sucesso!");
                                        break;
                                    }
                                    case 2:{
                                        System.out.println("\nDigite o codigo do livro");
                                        System.out.print("codigo: ");
                                        String codigo = ler.next();
                                        bio.deletarLivro(bio.getLivroSQL(codigo));
                                        System.out.print("\n");
                                        System.out.println("Livro deletado com sucesso!");
                                        break;
                                    }
                                }
                            } catch(InputMismatchException e) {
                                    System.out.println("Opcao invalida. Por favor, digite um valor entre 0 e 2");

                            }

                        } while(escolha != 0);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Opcao inválida. Por favor, digite um valor entre 0 e 4.");
                ler.next(); // Limpar buffer
            }
        } while (opcao != 0);
    }
    
    public void cadastro() throws Exception {

        int opcao = -1;
        
        do{ 
            try{
                
                System.out.println("\n===== Cadastro =====\n");
                System.out.println("Escolha uma das opcoes abaixo:\n ");
                System.out.println("[1] - Cadastrar usuario.\n");
                System.out.println("[2] - Cadastrar livro.\n");
                System.out.println("[0] - Voltar ao menu principal.\n");
                System.out.print("Digite a opcao: ");
                opcao = ler.nextInt();
                System.out.println("\n");
                
                switch(opcao){

                    case 1: {
                        
                        String nome; String sobrenome; String endereço; String CPF;
                        int dia; int mes; int ano; int codigo; boolean tentarNovamente = true;

                        while(tentarNovamente == true){
                            
                            try{
                                System.out.println("Pressione 0 para sair!\n");
                                
                                System.out.print("Digite o nome: ");
                                nome = ler.next();

                                if(nome.equals("0")){
                                    break;
                                }

                                if(!ValidaUsuarioEx.isNome(nome)){
                                    throw new ValidaUsuarioEx("Nome invalido");
                                }

                                System.out.print("Digite o sobrenome: ");
                                sobrenome = ler.next();
                                if(!ValidaUsuarioEx.isNome(sobrenome)){
                                    throw new ValidaUsuarioEx("Sobrenome invalido");
                                }
                                
                                System.out.println("Digite a data de nascimento: ");
                                System.out.print("Dia: ");
                                dia = ler.nextInt();
                                System.out.print("Mes: ");
                                mes = ler.nextInt();
                                System.out.print("Ano: ");
                                ano = ler.nextInt();
                                if(!ValidaUsuarioEx.isData(dia, mes, ano)){
                                    throw new ValidaUsuarioEx("Data invalida");
                                }
                                
                                System.out.print("CPF: ");
                                CPF = ler.next();
                                if(!ValidaUsuarioEx.isCPF(CPF)){
                                    throw new ValidaUsuarioEx("CPF invalido");
                                }

                                System.out.print("Digite o endereço: ");
                                ler.nextLine();
                                endereço = ler.nextLine();
                                if(!ValidaUsuarioEx.isEndereco(endereço)){
                                    throw new ValidaUsuarioEx("Endereço invalido");
                                }

                                System.out.print("Digite o codigo de usuario: ");
                                codigo = ler.nextInt();
                                if(!ValidaUsuarioEx.isCodigo(codigo)){
                                    throw new ValidaUsuarioEx("Codigo invalido");
                                }
                                
                                Usuário usuario = new Usuário(nome, sobrenome, dia, mes, ano, CPF, endereço, codigo);
                                bio.cadastraUsuarioSQL(usuario);
                                System.out.println("Usuario registrado com sucesso!");
                                break;
                            
                            } catch(ValidaUsuarioEx e) {
                                System.out.println("Erro de validacao");
                                ler.nextLine();
                                tentarNovamente = true;
                            } catch(ArrayIndexOutOfBoundsException e) {
                                System.out.println("Erro de validacao");
                                ler.nextLine();
                                tentarNovamente = true;
                            } 
                        }
                    break;
                    }

                    case 2: {
                        String tituloDoLivro; String categoria; String codigo;
                        int quantidade; boolean tentarNovamente = true;

                        while(tentarNovamente == true){
                            
                            try{
                                System.out.println("Digite 0 para sair!\n");
                                
                                System.out.print("Digite o titulo do livro: ");
                                ler.nextLine();
                                tituloDoLivro = ler.nextLine();
                                
                                 if(tituloDoLivro.equals("0")){
                                    break;
                                }
                                
                                if(!ValidaLivroEx.isTitulo(tituloDoLivro)){
                                    throw new ValidaLivroEx("Titulo invalido");
                                }
                                
                                System.out.print("Digite a categoria: ");
                                categoria = ler.next();
                                if(!ValidaLivroEx.isCategoria(categoria)){
                                    throw new ValidaLivroEx("Categoria invalida");
                                }

                                System.out.print("Digite a quantidade de livros: ");
                                quantidade = ler.nextInt();
                                if(!ValidaLivroEx.isQuantidade(quantidade)){
                                    throw new ValidaLivroEx("Quantidade invalida");
                                }

                                System.out.print("Digite o codigo do livro: ");
                                codigo = ler.next();
                                if(!ValidaLivroEx.isCodigo(codigo)){
                                    throw new ValidaLivroEx("Codigo invalido");
                                }
                                
                                Livro book = new Livro(codigo, tituloDoLivro, categoria, quantidade, 0);
                                bio.cadastraLivroSQL(book);
                                System.out.println("Livro registrado com sucesso!\n");
                                break;
                            
                            }catch(ValidaLivroEx e){
                                System.out.println("Erro de validacao");
                                ler.nextLine();
                                tentarNovamente = true;
                            }catch(InputMismatchException e){
                                System.out.println("Erro de validacao");
                                ler.nextLine();
                                tentarNovamente = true;
                            }
                        }
                    break;
                    }
                }
            }catch(InputMismatchException e){
                System.out.println("Opcao invalida. Por favor, digite um valor entre 0 e 3.\n");
                ler.nextLine();
                opcao = -1;
            }

        }while(opcao != 0);
    }

    public void emprestimo() throws Exception {
        int opcao = -1;
    
        do {
            try {
                System.out.println("\n===== Emprestimo ou devolucao =====\n");
                System.out.println("Escolha uma das opções abaixo: \n");
                System.out.println("[1] - Exibir o cadastro de livros.\n");
                System.out.println("[2] - Fazer ou renovar emprestimo de um livro.\n");
                System.out.println("[3] - Fazer devolucao de um livro.\n");
                System.out.println("[0] - Voltar ao menu principal.\n");
                System.out.print("Digite a opção: ");
                opcao = ler.nextInt();
                System.out.println("\n");
    
                switch (opcao) {
                    case 1: {
                        bio.imprimeLivrosSQL();
                        break;
                    }
    
                    case 2: {
                        boolean tentarNovamente = true;
                        int codigoUsuario;
                        String codigoLivro;
      
                        do {
                            try {
                                System.out.println("Pressione 0 para sair!\n");
                                System.out.print("Digite o código de usuário: ");
                                codigoUsuario = ler.nextInt();
                                 if(codigoUsuario == 0){
                                    break;
                                }
                                System.out.print("Digite o código do livro: ");
                                codigoLivro = ler.next();
                                Livro book = bio.getLivroSQL(codigoLivro);
                                Usuário usuario = bio.getUsuarioSQL(codigoUsuario);
                                
                                System.out.println("Digite a data para a devolucao:");
                                System.out.println("Dia: ");
                                int dia = ler.nextInt();
                                System.out.println("Mes: ");
                                int mes = ler.nextInt();
                                System.out.println("Ano: ");
                                int ano = ler.nextInt();
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(dia, mes - 1, ano);
                                Date devolucao = new Date(calendar.getTimeInMillis());
                                LocalDate hoje = LocalDate.now();
                                Date locacao = Date.valueOf(hoje);
                                System.out.println("");
                                
                                if(bio.getMaxLivro(codigoUsuario) == false) {
                                    System.out.println("Limite de emprestimos desse usuario foi atingido");
                                    break;
                                }
                                
                                bio.emprestaLivroSQL(usuario, book, devolucao, locacao);
                                System.out.println("O usuario " + usuario.getNome() + " fez emprestimo do livro " + book.getTituloDoLivro());
                                tentarNovamente = false;
                                break;

                            }catch (LivroNaoCadastradoEx e1) {
                                System.out.println(e1.getMessage());
                                tentarNovamente = true;
                            }catch (UsuarioNaoCadastradoEx e2) {
                                System.out.println(e2.getMessage());
                                tentarNovamente = true;
                            }catch (InputMismatchException e3) {
                                System.out.println("Erro de validação!");
                                tentarNovamente = true;
                            }
                        
                        } while (tentarNovamente);
                        break;
                    }
    
                    case 3: {
                        boolean tentarNovamente = true;
                        int codigoUsuario;
                        String codigoLivro;
                        LocalDate hoje = LocalDate.now();
    
                        do {
                            try {
                                System.out.println("Pressione 0 para sair!\n");
                                System.out.print("Digite o código de usuário: ");
                                codigoUsuario = ler.nextInt();
                                 if(codigoUsuario == 0){
                                    break;
                                }
                                System.out.print("Digite o código do livro: ");
                                codigoLivro = ler.next();
                                Livro livro = bio.getLivroSQL(codigoLivro);
                                Usuário usuario = bio.getUsuarioSQL(codigoUsuario);
                                
                                Date data_locacao = bio.getDiaLocacao(usuario, livro);
                                LocalDate dataLocacao = data_locacao.toLocalDate(); // Converter java.sql.Date para LocalDate
                                long diff = ChronoUnit.DAYS.between(dataLocacao, hoje); // Calcular a diferença em dias
                                
                                if(diff > maxDias){
                                    System.out.println("Voce entregou o livro com atraso maior que o limite de tolerancia");
                                }
                                
                                bio.devolveLivroSQL(usuario, livro);
                                System.out.println("O usuário " + usuario.getNome() + " devolveu o livro " + livro.getTituloDoLivro());
                                tentarNovamente = false;
                                break;
    
                            }catch (LivroNaoCadastradoEx e1) {
                                System.out.println(e1.toString());
                                tentarNovamente = true;
                            }catch (UsuarioNaoCadastradoEx e2) {
                                System.out.println(e2.getMessage());
                                tentarNovamente = true;
                            }catch (InputMismatchException e3) {
                                System.out.println("Erro de validação!");
                                tentarNovamente = true;
                            }catch (NenhumaCopiaEmprestadaEx e4) {
                                System.out.println(e4.getMessage());
                                tentarNovamente = true;
                            }
    
                        }while (tentarNovamente != false);
                        break;
                    }
                }

            }catch(InputMismatchException e){
                System.out.println("Opcao invalida. Por favor, digite um valor entre 0 e 3.\n");
                ler.nextLine();
                opcao = -1;
            }

        }while(opcao != 0);
     
    }

    public void relatorio() throws UsuarioNaoCadastradoEx{

        int opcao = -1;

        do{ 
            try{
                
                System.out.println("\n===== Relatorio =====\n");
                System.out.println("Escolha uma das opcoes abaixo: \n");
                System.out.println("[1] - Listar o acervo de livros.\n");
                System.out.println("[2] - Listar cadastro de usuarios.\n");
                System.out.println("[3] - Detalhes de um usuario ou um livro.\n");
                System.out.println("[0] - Voltar ao menu principal.\n");
                System.out.print("Digite a opcao: ");
                opcao = ler.nextInt();
                System.out.println("");
                
                switch(opcao){
            
                    case 1: {
                        bio.imprimeLivrosSQL();
                        break;
                    }
            
                    case 2: {
                        bio.imprimeUsuariosSQL();
                        break;
                    }
                        
                    case 3: {
                        int num = -1;
                        
                        do{
                            try{
                                System.out.println("[1] - Detalhes de um usuario. \n");
                                System.out.println("[2] - Detalhes de um livro. \n");
                                System.out.println("[0] - Voltar ao menu anterior. \n");
                                System.out.print("Digite a opcao: ");
                                System.out.println("");
                                num = ler.nextInt();
                                int codigoUsuario; String codigoLivro;

                                switch(num){
                                    
                                    case 1: {
                                        try{
                                            System.out.println("Digite 0 para sair!\n");
                                            System.out.print("Digite o codigo de usuario: ");
                                            codigoUsuario = ler.nextInt();
                                            if(codigoUsuario == 0){
                                               break;
                                            }
                                            Usuário usuario = bio.getUsuarioSQL(codigoUsuario);
                                            System.out.println(usuario.toString());
                                            bio.imprimeHistoricoUser(usuario);
                                            break;

                                        }catch(UsuarioNaoCadastradoEx e){
                                            System.out.println(e.getMessage());
                                            break;
                                        }                                        
                                    }
                                    case 2: {
                                        try{
                                            System.out.println("Digite 0 para sair!\n");
                                            System.out.print("Digite o codigo do livro: ");
                                            codigoLivro = ler.next();
                                            if(codigoLivro.equals("0")){
                                               break;
                                            }
                                            Livro book = bio.getLivroSQL(codigoLivro);
                                            book.toString();
                                            bio.imprimeHistoricoBook(book);
                                            break;

                                        }catch(LivroNaoCadastradoEx e){
                                            System.out.println(e.getMessage());
                                            break;
                                        }
                                    }

                                    case 0:{
                                        break;
                                    }

                                    default: {
                                        if(opcao != 0){
                                            System.out.println("Opcao invalida. Por favor, digite um valor entre 0 e 2.\n");
                                        }
                                        ler.nextLine();
                                        num = -1;
                                    }

                                }

                            }catch(InputMismatchException e1){
                                System.out.println("Opcao invalida. Por favor, digite um valor entre 0 e 2.\n");
                                ler.nextLine();
                                num = -1;
                            }

                        }while(num != 0);
                        break;
                    }                  
                }
            }catch(InputMismatchException e){
                System.out.println("Opcao invalida. Por favor, digite um valor entre 0 e 2.\n");
                ler.nextLine();
                opcao = -1;
            }
        
        }while(opcao != 0);
    }
    
    public void deletarConta() throws Exception {

        int opcao = -1;

        do {
            try {
                System.out.println("\n===== Deletar Conta =====\n");
                System.out.println("Deseja realmente deletar sua conta?: \n");
                System.out.println("[1] - Sim\n");
                System.out.println("[2] - Nao\n");
                System.out.print("Digite a opcao: ");
                opcao = ler.nextInt();
                System.out.println("");

                switch(opcao){

                    case 1: {
                        System.out.println("\nConfirme que é realmente você digitando seu login e senha!");
                        System.out.print("Login: ");
                        String login = ler.next();
                        System.out.print("Senha: ");
                        String senha = ler.next();
                        bio.deletarEmprestado();
                        bio.deletarLivros();
                        bio.deletarUsuarios();
                        ContasSQL.deletarCadastro(login, senha);
                        loginSistema();
                        opcao = 0;
                        break;
                    }
                    case 2: {
                        opcao = 0;
                        break;
                    }
                }

            } catch(InputMismatchException e1) {
                System.out.println();
            }
        } while(opcao != 0);
    }
}
    
