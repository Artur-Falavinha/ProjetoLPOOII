package br.com.projetolpooii.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Esta é uma classe utilitária que usa o padrão "Factory".
// A responsabilidade dela é centralizar a criação de conexões com o banco.
public final class FabricaDeConexao {

    // Centralizamos os dados de conexão aqui como constantes.
    // Assim, se a senha ou a porta do banco mudar, só alteramos neste arquivo,
    // e o resto do sistema (os DAOs) não precisa ser modificado.
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/projetolpooii?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "3492Sibila#"; // OBS: Senha "hardcoded" aqui para simplificar o projeto.

    // O construtor é privado para impedir que alguém crie uma instância (new FabricaDeConexao()).
    // Isso força o uso dos métodos estáticos (padrão de "Utility Class").
    private FabricaDeConexao() {
    }

    /**
     * Este é o método principal da classe, o "coração" dela.
     * Os DAOs (ClienteDAO, VeiculoDAO, etc.) chamam este método estático
     * sempre que precisam executar um SQL no banco.
     *
     * @return uma nova conexão JDBC com o banco.
     * @throws SQLException se o DriverManager não conseguir se conectar.
     */
    public static Connection obterConexao() throws SQLException {
        // O DriverManager é o gerenciador do JDBC.
        // Ele usa o driver do MySQL (que importamos no pom.xml) para
        // criar a conexão de fato, usando nossas constantes de URL, usuário e senha.
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}