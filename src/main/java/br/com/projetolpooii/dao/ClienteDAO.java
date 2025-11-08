package br.com.projetolpooii.dao;

import br.com.projetolpooii.infra.FabricaDeConexao;
import br.com.projetolpooii.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public Cliente inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nome, sobrenome, rg, cpf, endereco) VALUES (?,?,?,?,?)";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getSobrenome());
            ps.setString(3, cliente.getRg());
            ps.setString(4, cliente.getCpf());
            ps.setString(5, cliente.getEndereco());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
                }
            }
            return cliente;
        }
    }

    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nome = ?, sobrenome = ?, rg = ?, cpf = ?, endereco = ? WHERE id_cliente = ?";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getSobrenome());
            ps.setString(3, cliente.getRg());
            ps.setString(4, cliente.getCpf());
            ps.setString(5, cliente.getEndereco());
            ps.setInt(6, cliente.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int idCliente) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ps.executeUpdate();
        }
    }

    public boolean possuiLocacaoAtiva(int idCliente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM locacao WHERE id_cliente = ?";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        }
    }

    public List<Cliente> listarTodos() throws SQLException {
        String sql = "SELECT id_cliente, nome, sobrenome, rg, cpf, endereco FROM cliente ORDER BY nome";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        }
        return clientes;
    }

    public List<Cliente> buscarPorNomeOuDocumento(String termo) throws SQLException {
        String sql = "SELECT id_cliente, nome, sobrenome, rg, cpf, endereco FROM cliente " +
                "WHERE nome LIKE ? OR sobrenome LIKE ? OR cpf LIKE ? ORDER BY nome";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            String filtro = "%" + termo + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);
            ps.setString(3, filtro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clientes.add(mapearCliente(rs));
                }
            }
        }
        return clientes;
    }

    public Cliente buscarPorId(int idCliente) throws SQLException {
        String sql = "SELECT id_cliente, nome, sobrenome, rg, cpf, endereco FROM cliente WHERE id_cliente = ?";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
                return null;
            }
        }
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("id_cliente"),
                rs.getString("nome"),
                rs.getString("sobrenome"),
                rs.getString("rg"),
                rs.getString("cpf"),
                rs.getString("endereco")
        );
    }
}
