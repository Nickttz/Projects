package Exceptions;

public class LivroNaoCadastradoEx extends Exception {
    
    public LivroNaoCadastradoEx(){
        super();
    }

    @Override
    public String getMessage(){
        return "Livro nao cadastrado.";
    }
}
