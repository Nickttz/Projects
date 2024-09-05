package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    public Connection getConnection() throws SQLException {
        Connection conexão = DriverManager.getConnection("jdbc:postgresql://localhost:1992/Biblioteca", "postgres", "147258369n");
        return conexão;
    }
}
