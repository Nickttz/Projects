package Exceptions;

public class NenhumaCopiaEmprestadaEx extends Exception{

    public NenhumaCopiaEmprestadaEx(){
        super();
    }

    @Override
    public String getMessage(){
        return "Este livro nao emprestou nenhuma copia recentemente";
    }
}
