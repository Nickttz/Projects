package Exceptions;

public class UsuarioNaoCadastradoEx extends Exception {
 
    public UsuarioNaoCadastradoEx(){
        super();
    }

    @Override
    public String getMessage(){
        return "Usuario nao cadastrado.";
    }
}
