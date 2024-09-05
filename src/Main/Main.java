package Main;

import java.util.Scanner;

import Biblioteca.Biblioteca;
import Menu.Menu;

public class Main {
    
    static int maxDias;
    static int maxNum;
    
    public static void main(String args[]) throws Exception {

        Scanner ler = new Scanner(System.in);
        Biblioteca bio = new Biblioteca();

        Menu menu = new Menu(ler, bio, maxDias, maxNum);
        menu.loginSistema();
        ler.close();
    }
}