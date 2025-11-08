package br.com.projetolpooii.dao;

import br.com.projetolpooii.infra.FabricaDeConexao;
import br.com.projetolpooii.model.Locacao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LocacaoDAO {

    public Locacao registrar(int idVeiculo, Locacao locacao, int idCliente) throws SQLException {
        String sql = "INSERT INTO locacao (id_veiculo, id_cliente, dias, valor_total, data_locacao) VALUES (?,?,?,?,?)";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idVeiculo);
            ps.setInt(2, idCliente);
            ps.setInt(3, locacao.getDias());
            ps.setDouble(4, locacao.getValor());
            ps.setDate(5, new Date(locacao.getData().getTimeInMillis()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    locacao.setId(rs.getInt(1));
                }
            }
            return locacao;
        }
    }

    public void removerPorVeiculo(int idVeiculo) throws SQLException {
        String sql = "DELETE FROM locacao WHERE id_veiculo = ?";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idVeiculo);
            ps.executeUpdate();
        }
    }
}
