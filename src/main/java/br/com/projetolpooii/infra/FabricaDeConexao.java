package br.com.projetolpooii.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Classe responsável por criar conexões com o banco MySQL
public final class FabricaDeConexao {

    // Dados de conexão com o banco local
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/projetolpooii?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "3492Sibila#";

    // Construtor privado pra não deixar instanciar a classe
    private FabricaDeConexao() {
    }

    // Método estático que retorna uma nova conexão com o banco
    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
