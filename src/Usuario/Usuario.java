package Usuario;

public class Usuário extends Pessoa{
    
    private String endereço;
    private int códigoUsuário;
    private int maxLivro;
    
    public Usuário(String nome, String sobreNome, int dia, int mes, int ano, String CPF, String endereço, int códigoUsuário){
        super(nome, sobreNome, dia, mes, ano, CPF);
        this.endereço = endereço;
        this.códigoUsuário = códigoUsuário;
        this.maxLivro = maxLivro;
    }

    public String getEndereco(){
        return endereço;
    }

    public void setEndereco(String endereço){
        this.endereço = endereço;
    }

    public int getMaxLivro(){
        return maxLivro;
    }

    public void setMaxLivro(int maxLivro){
        this.maxLivro = maxLivro;
    }

    public int getCodigoUsuario(){
        return this.códigoUsuário;
    }

    public void setCodigoUsuario(int códigoUsuário){
        this.códigoUsuário = códigoUsuário;
    }
    
    public String toString(){
        return super.toString() + "\n" + "Codigo de usuario: " + códigoUsuário + "\n" + "Endereço: " + endereço + "\n";
    }
}
