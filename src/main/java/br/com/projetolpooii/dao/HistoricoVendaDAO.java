package br.com.projetolpooii.dao;

import br.com.projetolpooii.infra.FabricaDeConexao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

public class HistoricoVendaDAO {

    public void registrarVenda(int idVeiculo, double valorVenda, Calendar dataVenda) throws SQLException {
        String sql = "INSERT INTO historico_venda (id_veiculo, data_venda, valor_venda) VALUES (?,?,?)";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idVeiculo);
            ps.setDate(2, new Date(dataVenda.getTimeInMillis()));
            ps.setDouble(3, valorVenda);
            ps.executeUpdate();
        }
    }
}
