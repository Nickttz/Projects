package Exceptions;

public class CopiaNaoDisponivelEX extends Exception{
    
    public CopiaNaoDisponivelEX() {
        super();
    }

    @Override
    public String toString() {
        return "Todas as copias foram emprestadas.";
    }
}
