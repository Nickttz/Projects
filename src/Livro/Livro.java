package Livro;

import Exceptions.CopiaNaoDisponivelEX;
import Exceptions.NenhumaCopiaEmprestadaEx;

public class Livro {
    
    private String códigoDoLivro;
    private String tituloDoLivro;
    private String categoria;
    private int quantidade;
    private int emprestados;
    
    public Livro(String códigoDoLivro, String tituloDoLivro, String categoria, int quantidade, int emprestados) {
        this.códigoDoLivro = códigoDoLivro;
        this.tituloDoLivro = tituloDoLivro;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.emprestados = emprestados;
    }
    
    public String getCodigoDoLivro() {
        return this.códigoDoLivro;
    }

    public void setCodigoDoLivro(String códigoDoLivro) {
        this.códigoDoLivro = códigoDoLivro;
    }
    
    public String getTituloDoLivro() {
        return this.tituloDoLivro;
    }

    public void setTituloDoLivro(String tituloDoLivro) {
        this.tituloDoLivro = tituloDoLivro;
    }
    
    public String getCategoria(){
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getEmprestados() {
        return this.emprestados;
    }

    public void setEmprestados(int emprestados) {
        this.emprestados = emprestados;
    }

    public void empresta() throws CopiaNaoDisponivelEX {
        if(emprestados == quantidade) {
            throw new CopiaNaoDisponivelEX();
        }

        else{
            emprestados++;
        }
    }
    
    public void devolve() throws NenhumaCopiaEmprestadaEx {
        if(emprestados == 0){
            throw new NenhumaCopiaEmprestadaEx();
        }
        else{
            emprestados--;
        }
    }

    public String toString(){
        return "Titulo do livro: " + tituloDoLivro + "\n" + "Codigo do livro: " + códigoDoLivro + "\n" + "Categoria do livro: " + categoria + "\n" + "Quantidade total: " + quantidade + "\n" + "Quantidade de emprestados: " + emprestados + "\n" + "===Historico=== " + "\n";
    }
}
