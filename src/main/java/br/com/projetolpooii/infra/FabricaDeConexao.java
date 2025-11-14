package br.com.projetolpooii.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// classe utilitária que usa o padrão "Factory".
// centraliza a criação de conexões com o banco.
public final class FabricaDeConexao {

    // centralizamos os dados de conexão aqui como constantes.
    // se a senha ou a porta do banco mudar, só alteramos neste arquivo,
    // e o resto do sistema (os DAOs) não precisa ser modificado.
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/projetolpooii?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "3492Sibila#";

    // construtor é privado para impedir que alguém crie uma instância (new FabricaDeConexao()).
    // forçando o uso dos métodos estáticos (padrão de "Utility Class").
    private FabricaDeConexao() {
    }

    /**
     * método principal da classe
     * os DAOs (ClienteDAO, VeiculoDAO, etc.) chamam esse método estático
     * sempre que precisam executar um SQL no banco.
     *
     * @return uma nova conexão JDBC com o banco.
     * @throws SQLException se o DriverManager não conseguir se conectar.
     */
    public static Connection obterConexao() throws SQLException {
        // driverManager é o gerenciador do JDBC.
        // usa o driver do MySQL (que importamos no pom.xml) para
        // criar a conexão de fato, usando nossas constantes de URL, usuário e senha.
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}