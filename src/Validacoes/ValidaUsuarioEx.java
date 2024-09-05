package Validacoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;

import Conexao.Conexao;

public class ValidaUsuarioEx extends RuntimeException{ 
    private static String aux1 = "."; private static String aux2 = ""; private static String aux3 = "/"; private static String aux4 = "-"; private static String aux5 = " ";
    private Conexao conexao;

    public ValidaUsuarioEx(String mensagem){
        super(mensagem);
    }

    public static boolean usuarioExiste(String usuario) {
        String query = "SELECT COUNT(*) FROM login WHERE usuario = ?";
        
        try {
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmt = conexao.prepareStatement(query);
            psmt.setString(1, usuario);
            ResultSet rs = psmt.executeQuery();
            
            if(rs.next()){
                int count = rs.getInt(1);
                if(count == 1){
                    return true;
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean verificaSenha(String usuario, String senha) {
        String query = "SELECT senha FROM login WHERE usuario = ?";
        
        try {
            Connection conexao = new Conexao().getConnection();
            PreparedStatement psmt = conexao.prepareStatement(query);
            psmt.setString(1, usuario);
            ResultSet rs = psmt.executeQuery();
            
            if (rs.next()) {
                return senha.equals(rs.getString("senha"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean isNome(String name){

        try{

            String alfabeto = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
            int cont = 0;
            
            for(int i = 0; i <= (name.length() - 1); i++){
                for(int j = 0; j <= (alfabeto.length() - 1); j++){
                    if(name.charAt(i) == alfabeto.charAt(j)){
                        cont = cont + 1;
                        break;
                    }              
                    else{
                        continue;
                    }
                }
            }
            if(cont == name.length()){
                return true;
            }
            else{
                return false;
            }
        
        }catch(InputMismatchException e){
            return false;
        }
    }

    public static boolean isCPF(String CPF){
        
        CPF = CPF.replace(aux1, aux2);
        CPF = CPF.replace(aux3, aux2);
        CPF = CPF.replace(aux4, aux2);
        CPF = CPF.replace(aux5, aux2);
    
        if(CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222") || CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555") || 
        CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888") || CPF.equals("99999999999") || (CPF.length() != 11) || CPF.isEmpty()){
            
            return false;
        }
        
        char dig10, dig11;
        int soma, i, resto, num, peso;

        try{
            soma = 0;
            peso = 10;
            
            for(i = 0; i < 9; i++){
                num = (int)(CPF.charAt(i) - 48);
                soma = soma + (num * peso);
                peso = peso - 1;
            }

            resto = 11 - (soma % 11);
            
            if((resto == 10) || (resto == 11)){
                dig10 = '0';
            }
            
            else{
                dig10 = (char)(resto + 48);
            }
            
            soma = 0;
            peso = 11;
            
            for(i = 0; i < 10; i++){
                num = (int)(CPF.charAt(i) - 48);
                soma = soma + (num * peso);
                peso = peso - 1;
            }

            resto = 11 - (soma % 11);
            
            if((resto == 10) || (resto == 11)){
                 dig11 = '0';
            }

            else{
                dig11 = (char)(resto + 48);
            }

            if((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))){
                return true;
            }
            
            else{ 
                return false;
            }        
        
        }catch(InputMismatchException e){
            return false;
        }
    }

    public static boolean isData(int dia, int mes, int ano){
        
        int[] dias = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        GregorianCalendar hoje = new GregorianCalendar();

        if(ano < 1918 || ano > hoje.get(Calendar.YEAR)){
            return false;
        }
        
        try{
            if(dia < 1){
                return false;
            }
            if(mes == 2){
                if(ano%4 == 0){
                    if(dia <= 29){
                        return true;
                    }
                    else{
                        return false;
                    }
                }    
                else{
                    if(dia <= 28){
                        return true;
                    }    
                    else{
                        return false;
                    }
                } 
            }
            else{
                for(int i = 0; i <= (mes-1); i++){ 
                    if(i == (mes-1)){
                        if(dias[i] < dia){
                            return false;
                        }  
                        else{
                            return true;
                        }
                    }
                    else{
                        continue;
                    }
                }
            }
            return false;
        
        }catch(InputMismatchException e){
            return false;
        }
    }

    public static boolean isEndereco(String endereco){
        
        try{
            if(endereco.isEmpty()){ 
                return false;
            }
            else{
                return true;
            }
        }catch(InputMismatchException e){
            return false;
        }
    }

    public static boolean isCodigo(int codigo){

        try{

            if(codigo < 0){
                return false;
            }

            else{
                return true;
            }

        }catch(InputMismatchException e){
            return false;
        }
    }
}


