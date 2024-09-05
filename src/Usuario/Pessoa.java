package Usuario;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Pessoa{

    private String nome;
    private String sobreNome;
    private GregorianCalendar DataNasc;
    private int dia; int mes; int ano;
    private String CPF;
    
    public Pessoa(String nome, String sobreNome, int dia, int mes, int ano){
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.DataNasc = new GregorianCalendar(ano, mes, dia);
    }
    
    public Pessoa(String nome, String sobreNome, int dia, int mes, int ano, String CPF){
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.DataNasc = new GregorianCalendar(ano, mes, dia);
        this.CPF = CPF;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getsobreNome(){
        return this.sobreNome;
    }

    public void setsobreNome(String sobreNome){
        this.sobreNome = sobreNome;
    }

    public GregorianCalendar getDataNasc(){
        return this.DataNasc;
    }

    public int getMes(){
        if(DataNasc.get(Calendar.MONTH) == 0){ 
            return 12;
        }
        else{
            return DataNasc.get(Calendar.MONTH);
        }
    }

    public void setDataNasc(GregorianCalendar DataNasc){
        this.DataNasc = DataNasc;
    }

    public String getCPF(){
        return this.CPF;
    }

    public void setCPF(String CPF){
        this.CPF = CPF;
    }

    @Override
    public String toString(){
        return "Nome: " + nome + "\n" + "Sobrenome: " + sobreNome + "\n" + "Data de nascimento: " + DataNasc.get(Calendar.DAY_OF_MONTH) + "/" + getMes() + "/" + DataNasc.get(Calendar.YEAR) + "\n" + "CPF: " + CPF; 
    }
}
