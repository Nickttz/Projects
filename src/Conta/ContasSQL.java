package Conta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Conexao.Conexao;

public class ContasSQL {
    
    static String id_login;

    public static void setIDlogin(String dados) {
        id_login = dados;
    }

    public static String getIDlogin(){
        return id_login;
    }

    public static void deletarCadastro(String login, String senha){
        String query = "DELETE FROM login WHERE usuario = ? AND senha = ?";

        try{
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmt = conexao.prepareStatement(query);

            psmt.setString(1, login);
            psmt.setString(2, senha);

            int linhas_afetadas = psmt.executeUpdate();

            if (linhas_afetadas > 0){
                System.out.println("Sua conta foi deletada com sucesso!\n");
            }
            else{
                System.out.println("Erro ao deletar conta!\n");
            }
    
        } catch (SQLException e){
            e.printStackTrace();
        }
    }   
    
    public static void criarLogin(String usuario, String senha, int max_dias, int max_livros) {
        String query = "INSERT INTO login (usuario, senha, max_dias, max_livros) VALUES (?, ?, ?, ?)";

        try {
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmt = conexao.prepareStatement(query);
            psmt.setString(1, usuario);
            psmt.setString(2, senha);
            psmt.setInt(3, max_dias);
            psmt.setInt(4, max_livros);

            int linhas_afetadas = psmt.executeUpdate();

            if (linhas_afetadas > 0) {
                System.out.println("Novo usuario criado e politicas definidas com sucesso.\n");
            } 
            else {
                System.out.println("Erro ao criar o novo usuario. Tente novamente.\n");
            }

        } catch (SQLException e) {
            // Verifica se a exceção é devido a uma violação de chave única
            if (e.getSQLState().equals("23505")) { // Código de estado SQL para violação de chave única em PostgreSQL
            System.out.println("Erro: O usuario ja existe. Por favor, escolha um nome de usuario diferente.");
            }
            else {
                e.printStackTrace();
            }
        }
    }


}