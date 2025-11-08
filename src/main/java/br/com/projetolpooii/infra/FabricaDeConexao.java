package br.com.projetolpooii.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class FabricaDeConexao {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/projetolpooii?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "3492Sibila#";

    private FabricaDeConexao() {
    }

    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
