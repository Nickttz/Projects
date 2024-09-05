package Validacoes;

import java.util.InputMismatchException;

public class ValidaLivroEx extends RuntimeException{

    public ValidaLivroEx(String mensagem){
        super(mensagem);
    }

    public static boolean isTitulo(String Titulo){
        try{

            if(Titulo.isEmpty()){
                return false;
            }

            else{
                return true;
            }

        }catch(InputMismatchException e){
            return false;
        }
    }

    public static boolean isCodigo(String codigo){
        try{

            int codigoint = Integer.parseInt(codigo); 

            if(codigo.isEmpty()){
                return false;
            }

            if(codigoint < 0){
                return false;
            }

            else{
                return true;
            }

        }catch(InputMismatchException e){
            return false;
        }
    }

    public static boolean isCategoria(String categoria){

        try{

            String alfabeto = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
            int cont = 0;
            
            for(int i = 0; i <= (categoria.length() - 1); i++){
                for(int j = 0; j <= (alfabeto.length() - 1); j++){
                    if(categoria.charAt(i) == alfabeto.charAt(j)){
                        cont = cont + 1;
                        break;
                    }              
                    else{
                        continue;
                    }
                }
            }
            if(cont == categoria.length()){
                return true;
            }
            else{
                return false;
            }
        
        }catch(InputMismatchException e){
            return false;
        }
    }

    public static boolean isQuantidade(int quantidade){

        try{

            if(quantidade <= 0){
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


