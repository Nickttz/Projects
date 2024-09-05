package Biblioteca;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import Conexao.Conexao;
import Conta.ContasSQL;
import Exceptions.CopiaNaoDisponivelEX;
import Exceptions.LivroNaoCadastradoEx;
import Exceptions.NenhumaCopiaEmprestadaEx;
import Exceptions.UsuarioNaoCadastradoEx;
import Livro.Livro;
import Usuario.Usuário;

public class Biblioteca{

    public Biblioteca(){}

    public boolean getMaxLivro(int cod){
        String query = "SELECT COUNT(*) FROM emprestado WHERE id_usuario = ? AND id_login = ?";
        String query2 = "SELECT max_livros FROM login WHERE usuario = ?";
        int cont = 0;

        try {
            Connection conexao = new Conexao().getConnection();
            
            PreparedStatement psmt = conexao.prepareStatement(query);
            psmt.setInt(1, cod);
            psmt.setString(2, ContasSQL.getIDlogin());
            ResultSet rs = psmt.executeQuery();

            if(rs.next()){
                cont = rs.getInt(1);
            }

            PreparedStatement psmt2 = conexao.prepareStatement(query2);
            psmt2.setString(1, ContasSQL.getIDlogin());
            ResultSet rs2 = psmt2.executeQuery();

            if(rs2.next()) {
                int max_livros = rs2.getInt(1);
                if(cont >= max_livros) {
                    return false;
                }
                else {
                    return true;
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void devolveLivroSQL(Usuário user, Livro book) throws NenhumaCopiaEmprestadaEx, LivroNaoCadastradoEx{
        String query = "DELETE FROM emprestado WHERE id_login = ? AND codigo = ? AND id_usuario = ?";
        String verificaLivro = "SELECT emprestados FROM livro WHERE id_login = ? AND id_livro = ?";
        String atualizarLivro = "UPDATE livro SET quantidade = quantidade + 1, emprestados = emprestados - 1 WHERE id_login = ? AND id_livro = ?";

        try{
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmtVerifica = conexao.prepareStatement(verificaLivro);
            psmtVerifica.setString(1, ContasSQL.getIDlogin());
            psmtVerifica.setString(2, book.getCodigoDoLivro());
            ResultSet rs = psmtVerifica.executeQuery();
            
            if(rs.next()){
                
                int quant = rs.getInt("emprestados");
                
                if(quant > 0){
                    PreparedStatement psmtAtualiza = conexao.prepareStatement(atualizarLivro);
                    PreparedStatement psmt = conexao.prepareStatement(query);
                
                    psmt.setString(1, ContasSQL.getIDlogin());
                    psmt.setString(2, book.getCodigoDoLivro());
                    psmt.setInt(3, user.getCodigoUsuario());
                    psmt.executeUpdate();

                    psmtAtualiza.setString(1, ContasSQL.getIDlogin());
                    psmtAtualiza.setString(2, book.getCodigoDoLivro());
                    psmtAtualiza.executeUpdate();
                    System.out.println("Devolucao realizada com sucesso!");

                    psmt.close();
                    psmtAtualiza.close();
                }
                else{
                    throw new NenhumaCopiaEmprestadaEx();
                }
            }
            else{
                throw new LivroNaoCadastradoEx();
            }
            psmtVerifica.close();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void deletarLivro(Livro book) throws LivroNaoCadastradoEx {
        String query = "DELETE FROM livro WHERE id_login = ? AND id_livro = ?";
        String verificaLivro = "SELECT COUNT(*) FROM livro WHERE id_login = ?";

        try{
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmtVerifica = conexao.prepareStatement(verificaLivro);
            psmtVerifica.setString(1, ContasSQL.getIDlogin());
            ResultSet rs = psmtVerifica.executeQuery();

            if(rs.next()){
                PreparedStatement psmt = conexao.prepareStatement(query);
                psmt.setString(1, book.getCodigoDoLivro());
                psmt.setString(2, ContasSQL.getIDlogin());
                psmt.executeUpdate();
            } 
            else{
                throw new LivroNaoCadastradoEx();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deletarUsuario(Usuário user) throws UsuarioNaoCadastradoEx {
        String query = "DELETE FROM usuario WHERE id_login = ? AND id_usuario = ?";
        String verificaUsuario = "SELECT COUNT(*) FROM usuario WHERE id_login = ?";

        try{
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmtVerifica = conexao.prepareStatement(verificaUsuario);
            psmtVerifica.setString(1, ContasSQL.getIDlogin());
            ResultSet rs = psmtVerifica.executeQuery();

            if(rs.next()){
                PreparedStatement psmt = conexao.prepareStatement(query);
                psmt.setInt(1, user.getCodigoUsuario());
                psmt.setString(2, ContasSQL.getIDlogin());
                psmt.executeUpdate();
            } 
            else{
                throw new UsuarioNaoCadastradoEx();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void emprestaLivroSQL(Usuário user, Livro book, Date devolucao, Date locacao) throws CopiaNaoDisponivelEX, LivroNaoCadastradoEx{
        String query = "INSERT INTO emprestado(titulo, codigo, id_usuario, id_login, categoria, locacao, devolucao) values(?, ?, ?, ?, ?, ?, ?)";
        String verificaLivro = "SELECT quantidade FROM livro WHERE id_login = ? AND id_livro = ?";
        String atualizarLivro = "UPDATE livro SET quantidade = quantidade - 1, emprestados = emprestados + 1 WHERE id_login = ? AND id_livro = ?";

        try{
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmtVerifica = conexao.prepareStatement(verificaLivro);
            psmtVerifica.setString(1, ContasSQL.getIDlogin());
            psmtVerifica.setString(2, book.getCodigoDoLivro());
            ResultSet rs = psmtVerifica.executeQuery();
            
            if(rs.next()){
                
                int quant = rs.getInt("quantidade");
                
                if(quant > 0){
                    PreparedStatement psmtAtualiza = conexao.prepareStatement(atualizarLivro);
                    PreparedStatement psmt = conexao.prepareStatement(query);
                    
                    psmt.setString(1, book.getTituloDoLivro());
                    psmt.setString(2, book.getCodigoDoLivro());
                    psmt.setInt(3, user.getCodigoUsuario());
                    psmt.setString(4, ContasSQL.getIDlogin());
                    psmt.setString(5, book.getCategoria());
                    psmt.setDate(6, devolucao);
                    psmt.setDate(7, locacao);
                    psmt.executeUpdate();

                    psmtAtualiza.setString(1, ContasSQL.getIDlogin());
                    psmtAtualiza.setString(2, book.getCodigoDoLivro());
                    psmtAtualiza.executeUpdate();
                    System.out.println("Emprestimo realizado com sucesso!");

                    psmt.close();
                    psmtAtualiza.close();
                }
                else{
                    throw new CopiaNaoDisponivelEX();
                }
            }
            else{
                throw new LivroNaoCadastradoEx();
            }
            psmtVerifica.close();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void imprimeUsuariosSQL(){
        String query = "SELECT * FROM usuario WHERE id_login = ?";
        String verificarUser = "SELECT COUNT(*) FROM usuario WHERE id_login = ?";

        try{
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmtVerifica = conexao.prepareStatement(verificarUser);
            psmtVerifica.setString(1, ContasSQL.getIDlogin());
            ResultSet rs = psmtVerifica.executeQuery();
            
            if(rs.next()){
                int count = rs.getInt(1);
                if(count == 0){
                    System.out.println("Nao ha usuarios em seu registro!");
                }
            }

            PreparedStatement psmt = conexao.prepareStatement(query);
            psmt.setString(1, ContasSQL.getIDlogin());
            ResultSet rs2 = psmt.executeQuery();

            while(rs2.next()){
                String nome = rs2.getString("nome");
                String sobrenome = rs2.getString("sobrenome");
                Date data_nascimento = rs2.getDate("data_nascimento");
                String cpf = rs2.getString("cpf");
                String endereco = rs2.getString("endereco");
                String iduser = rs2.getString("id_usuario");

                System.out.println("Nome: " + nome);
                System.out.println("Sobrenome: " + sobrenome);
                System.out.println("Data de nascimento: " + data_nascimento);
                System.out.println("CPF: " + cpf);
                System.out.println("Endereco: " + endereco);
                System.out.println("ID Usuario: " + iduser);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void imprimeLivrosSQL(){
        String query = "SELECT * FROM livro WHERE id_login = ?";
        String verificarUser = "SELECT COUNT(*) FROM usuario WHERE id_login = ?";

        try{
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmtVerifica = conexao.prepareStatement(verificarUser);
            psmtVerifica.setString(1, ContasSQL.getIDlogin());
            ResultSet rs = psmtVerifica.executeQuery();
            
            if(rs.next()){
                int count = rs.getInt(1);
                if(count == 0){
                    System.out.println("Nao ha livros em seu registro!");
                }
            }

            PreparedStatement psmt = conexao.prepareStatement(query);
            psmt.setString(1, ContasSQL.getIDlogin());
            ResultSet rs2 = psmt.executeQuery();

            while(rs2.next()){
                String titulo = rs2.getString("titulo_livro");
                String categoria = rs2.getString("categoria");
                int quantidade = rs2.getInt("quantidade");
                int emprestados = rs2.getInt("emprestados");
                String idlivro = rs2.getString("id_livro");

                System.out.println("Titulo do livro: " + titulo);
                System.out.println("Categoria: " + categoria);
                System.out.println("Quantidade: " + quantidade);
                System.out.println("Quantidade de livros emprestados: " + emprestados);
                System.out.println("ID Livro: " + idlivro + "\n");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deletarUsuarios(){
        String query = "DELETE FROM usuario WHERE id_login = ?";
        String verificarUser = "SELECT COUNT(*) FROM usuario WHERE id_login = ?";

        try{
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmtVerifica = conexao.prepareStatement(verificarUser);
            psmtVerifica.setString(1, ContasSQL.getIDlogin());
            ResultSet rs = psmtVerifica.executeQuery();
            
            if(rs.next()){
                int count = rs.getInt(1);
                if(count == 0){
                    System.out.println("Nao ha usuarios em seus registros");
                    return;
                }
            }    
            PreparedStatement psmt = conexao.prepareStatement(query);
            psmt.setString(1, ContasSQL.getIDlogin());
            int excluidos = psmt.executeUpdate();
            System.out.println("Usuarios excluidos: " + excluidos);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deletarLivros(){
        String query = "DELETE FROM livro WHERE id_login = ?";
        String verificarLivro = "SELECT COUNT(*) FROM livro WHERE id_login = ?";
            
        try {
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmtVerifica = conexao.prepareStatement(verificarLivro);
            psmtVerifica.setString(1, ContasSQL.getIDlogin());
            ResultSet rs = psmtVerifica.executeQuery();

            if(rs.next()){
                int count = rs.getInt(1);
                if(count == 0){
                    System.out.println("Nao ha livros em seu acervo");
                    return;
                }
            }
        
            PreparedStatement psmt = conexao.prepareStatement(query);
            psmt.setString(1, ContasSQL.getIDlogin());
            int excluidos = psmt.executeUpdate();
            System.out.println("Livros excluidos: " + excluidos);
            
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deletarEmprestado(){
        
        String query = "DELETE FROM emprestado WHERE id_login = ?";
            
        try {
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmt = conexao.prepareStatement(query);
            psmt.setString(1, ContasSQL.getIDlogin());
            psmt.executeUpdate();
        
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void cadastraUsuarioSQL(Usuário user) {

        String query = "INSERT INTO usuario(nome, sobrenome, data_nascimento, cpf, endereco, id_usuario, id_login) values(?, ?, ?, ?, ?, ?, ?)";

        try {  
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmt = conexao.prepareStatement(query);

            psmt.setString(1, user.getNome());
            psmt.setString(2, user.getsobreNome());

            GregorianCalendar calendar = user.getDataNasc(); //Converte GregorianCalendar para java.sql.Date
            java.util.Date utilDate = calendar.getTime();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            
            psmt.setDate(3, sqlDate);
            psmt.setString(4, user.getCPF());
            psmt.setString(5, user.getEndereco());
            psmt.setInt(6, user.getCodigoUsuario());
            psmt.setString(7, ContasSQL.getIDlogin());  

            psmt.executeUpdate(); // Executa
            psmt.close(); // Fecha o statment

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cadastraLivroSQL(Livro book) {

        String query = "INSERT INTO livro(titulo_livro, categoria, quantidade, emprestados, id_livro, id_login) values(?, ?, ?, ?, ?, ?)";

        try {  
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmt = conexao.prepareStatement(query);

            psmt.setString(1, book.getTituloDoLivro());
            psmt.setString(2, book.getCategoria());
            psmt.setInt(3, book.getQuantidade());
            psmt.setInt(4, book.getEmprestados());
            psmt.setString(6, book.getCodigoDoLivro());
            psmt.setString(7, ContasSQL.getIDlogin());
            
            psmt.executeUpdate(); // Executa
            psmt.close(); // Fecha o statment

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Date getDiaLocacao(Usuário user, Livro book){
        String query = "SELECT locacao FROM emprestado WHERE id_login = ? AND id_usuario = ? AND codigo = ?";

        try{
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmt = conexao.prepareStatement(query);
            psmt.setString(1, ContasSQL.getIDlogin());
            psmt.setInt(2, user.getCodigoUsuario());
            psmt.setString(3, book.getCodigoDoLivro());
            ResultSet rs = psmt.executeQuery();
            
            if(rs.next()){
                Date data = rs.getDate("locacao");
                return data;

            }
            else{
                System.out.println("Esse usuario nao efetuou o emprestimo deste livro!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void imprimeHistoricoUser(Usuário user){
        String queryHistoric = "SELECT * FROM emprestado WHERE id_login = ? AND id_usuario = ?";

        try{
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmt = conexao.prepareStatement(queryHistoric);
            psmt.setString(1, ContasSQL.getIDlogin());
            psmt.setInt(2, user.getCodigoUsuario());
            ResultSet rs = psmt.executeQuery();

            if(rs.next()){
                System.out.print("Livros emprestados: " + "\n");
                do {
                    System.out.println("Titulo: " + rs.getString("titulo"));
                    System.out.println("Codigo: " + rs.getString("codigo"));
                    System.out.println("");
                } while(rs.next());
            }
            else {
                System.out.println("Livros emprestados: Vazio");
            } 
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void imprimeHistoricoBook(Livro book) throws UsuarioNaoCadastradoEx{
        String queryHistoric = "SELECT * FROM emprestado WHERE id_login = ? AND id_usuario = ?";
        ArrayList<String> historic = new ArrayList<>();

        try {
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmt = conexao.prepareStatement(queryHistoric);
            psmt.setString(1, ContasSQL.getIDlogin());
            psmt.setString(2, book.getCodigoDoLivro());
            ResultSet rs = psmt.executeQuery();

            if(rs.next()) {
                System.out.print("Usuarios que pediram emprestimo: ");
                while(rs.next()){
                    int cod = rs.getInt("id_usuario");
                    Usuário user = getUsuarioSQL(cod);
                    historic.add("Nome: " + user.getNome() + "\n");
                    historic.add("Codigo: " + cod + "\n");
                }
                for(String info : historic) {
                    System.out.println(info);
                }
            }
            else {
                System.out.println("Usuarios que pediram emprestimo: Vazio");
            } 
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuário getUsuarioSQL(int cod) throws UsuarioNaoCadastradoEx {
        String query = "SELECT * FROM usuario WHERE id_usuario = ? AND id_login = ?";

        try{
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmt = conexao.prepareStatement(query);
            psmt.setInt(1, cod);
            psmt.setString(2, ContasSQL.getIDlogin());
            ResultSet rs = psmt.executeQuery();

            if(rs.next()){
                String nome = rs.getString("nome");
                String sobrenome = rs.getString("sobrenome");
                
                Date data = rs.getDate("data_nascimento"); 
                Calendar calendar = Calendar.getInstance(); //ARTIFICIO PARA PEGAR DIA, MES E ANO DE UM TIPO DATE
                calendar.setTime(data);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);
                
                String CPF = rs.getString("cpf");
                String endereco = rs.getString("endereco");
                Usuário user = new Usuário(nome, sobrenome, dia, mes, ano, CPF, endereco, cod);
                return user;
            }
            else{
                throw new UsuarioNaoCadastradoEx();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public Livro getLivroSQL(String cod) throws LivroNaoCadastradoEx {
        String query = "SELECT * FROM livro WHERE id_livro = ? AND id_login = ?";

        try{
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmt = conexao.prepareStatement(query);
            psmt.setString(1, cod);
            psmt.setString(2, ContasSQL.getIDlogin());
            ResultSet rs = psmt.executeQuery();

            if(rs.next()){
                String titulo = rs.getString("titulo_livro");
                String categoria = rs.getString("categoria");
                int quantidade = rs.getInt("quantidade");
                int emprestados = rs.getInt("emprestados");
                Livro book = new Livro(cod, titulo, categoria, quantidade, emprestados);
                System.out.println("Livro encontrado com sucesso!");
                return book;
            }
            else{
                throw new LivroNaoCadastradoEx();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}