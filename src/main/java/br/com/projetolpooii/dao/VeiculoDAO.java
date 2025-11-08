package br.com.projetolpooii.dao;

import br.com.projetolpooii.infra.FabricaDeConexao;
import br.com.projetolpooii.model.Automovel;
import br.com.projetolpooii.model.Cliente;
import br.com.projetolpooii.model.Locacao;
import br.com.projetolpooii.model.Motocicleta;
import br.com.projetolpooii.model.Van;
import br.com.projetolpooii.model.Veiculo;
import br.com.projetolpooii.model.enums.Categoria;
import br.com.projetolpooii.model.enums.Estado;
import br.com.projetolpooii.model.enums.Marca;
import br.com.projetolpooii.model.enums.ModeloAutomovel;
import br.com.projetolpooii.model.enums.ModeloMotocicleta;
import br.com.projetolpooii.model.enums.ModeloVan;
import br.com.projetolpooii.model.enums.TipoVeiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VeiculoDAO {

    public Veiculo inserir(Veiculo veiculo, TipoVeiculo tipo, String modelo) throws SQLException {
        String sql = "INSERT INTO veiculo (tipo, marca, categoria, estado, modelo, placa, ano, valor_compra, valor_locacao_diaria) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, tipo.name());
            ps.setString(2, veiculo.getMarca().name());
            ps.setString(3, veiculo.getCategoria().name());
            ps.setString(4, veiculo.getEstado().name());
            ps.setString(5, modelo);
            ps.setString(6, veiculo.getPlaca());
            ps.setInt(7, veiculo.getAno());
            ps.setDouble(8, veiculo.getValorDeCompra());
            ps.setDouble(9, veiculo.getValorDiariaLocacao());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    veiculo.setId(rs.getInt(1));
                }
            }
        }
        return veiculo;
    }

    public void atualizarEstado(int idVeiculo, Estado novoEstado) throws SQLException {
        String sql = "UPDATE veiculo SET estado = ? WHERE id_veiculo = ?";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, novoEstado.name());
            ps.setInt(2, idVeiculo);
            ps.executeUpdate();
        }
    }

    public boolean placaExiste(String placa) throws SQLException {
        String sql = "SELECT COUNT(*) FROM veiculo WHERE placa = ?";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        }
    }

    public Veiculo buscarPorId(int idVeiculo, ClienteDAO clienteDAO) throws SQLException {
        String sql = "SELECT * FROM veiculo v LEFT JOIN locacao l ON l.id_veiculo = v.id_veiculo WHERE v.id_veiculo = ?";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, idVeiculo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearVeiculo(rs, clienteDAO);
                }
                return null;
            }
        }
    }

    public List<Veiculo> listarDisponiveisParaLocacao(String termoMarca,
                                                       Categoria categoriaFiltro,
                                                       TipoVeiculo tipoFiltro,
                                                       ClienteDAO clienteDAO) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM veiculo v LEFT JOIN locacao l ON l.id_veiculo = v.id_veiculo WHERE v.estado = 'DISPONIVEL'");
        List<Object> parametros = new ArrayList<>();
        if (termoMarca != null && !termoMarca.isBlank()) {
            sql.append(" AND v.marca LIKE ?");
            parametros.add("%" + termoMarca + "%");
        }
        if (categoriaFiltro != null) {
            sql.append(" AND v.categoria = ?");
            parametros.add(categoriaFiltro.name());
        }
        if (tipoFiltro != null) {
            sql.append(" AND v.tipo = ?");
            parametros.add(tipoFiltro.name());
        }
        sql.append(" ORDER BY v.marca, v.modelo");

        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = prepararComando(conexao, sql.toString(), parametros);
             ResultSet rs = ps.executeQuery()) {
            List<Veiculo> veiculos = new ArrayList<>();
            while (rs.next()) {
                veiculos.add(mapearVeiculo(rs, clienteDAO));
            }
            return veiculos;
        }
    }

    public List<Veiculo> listarLocados(ClienteDAO clienteDAO) throws SQLException {
        String sql = "SELECT * FROM veiculo v INNER JOIN locacao l ON l.id_veiculo = v.id_veiculo WHERE v.estado = 'LOCADO'";
        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Veiculo> veiculos = new ArrayList<>();
            while (rs.next()) {
                veiculos.add(mapearVeiculo(rs, clienteDAO));
            }
            return veiculos;
        }
    }

    public List<Veiculo> listarDisponiveisParaVenda(String termoMarca,
                                                     Categoria categoriaFiltro,
                                                     TipoVeiculo tipoFiltro,
                                                     ClienteDAO clienteDAO) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM veiculo v LEFT JOIN locacao l ON l.id_veiculo = v.id_veiculo WHERE v.estado = 'DISPONIVEL'");
        List<Object> parametros = new ArrayList<>();
        if (termoMarca != null && !termoMarca.isBlank()) {
            sql.append(" AND v.marca LIKE ?");
            parametros.add("%" + termoMarca + "%");
        }
        if (categoriaFiltro != null) {
            sql.append(" AND v.categoria = ?");
            parametros.add(categoriaFiltro.name());
        }
        if (tipoFiltro != null) {
            sql.append(" AND v.tipo = ?");
            parametros.add(tipoFiltro.name());
        }
        sql.append(" ORDER BY v.marca, v.modelo");

        try (Connection conexao = FabricaDeConexao.obterConexao();
             PreparedStatement ps = prepararComando(conexao, sql.toString(), parametros);
             ResultSet rs = ps.executeQuery()) {
            List<Veiculo> veiculos = new ArrayList<>();
            while (rs.next()) {
                veiculos.add(mapearVeiculo(rs, clienteDAO));
            }
            return veiculos;
        }
    }

    private PreparedStatement prepararComando(Connection conexao, String sql, List<Object> parametros) throws SQLException {
        PreparedStatement ps = conexao.prepareStatement(sql);
        for (int i = 0; i < parametros.size(); i++) {
            ps.setObject(i + 1, parametros.get(i));
        }
        return ps;
    }

    private Veiculo mapearVeiculo(ResultSet rs, ClienteDAO clienteDAO) throws SQLException {
        TipoVeiculo tipo = TipoVeiculo.valueOf(rs.getString("tipo"));
        Marca marca = Marca.valueOf(rs.getString("marca"));
        Categoria categoria = Categoria.valueOf(rs.getString("categoria"));
        Estado estado = Estado.valueOf(rs.getString("estado"));
        String placa = rs.getString("placa");
        int ano = rs.getInt("ano");
        double valorCompra = rs.getDouble("valor_compra");
        int id = rs.getInt("id_veiculo");
        String modelo = rs.getString("modelo");

        Veiculo veiculo = switch (tipo) {
            case AUTOMOVEL -> new Automovel(id, marca, estado, categoria, valorCompra, placa, ano,
                    ModeloAutomovel.valueOf(modelo));
            case MOTOCICLETA -> new Motocicleta(id, marca, estado, categoria, valorCompra, placa, ano,
                    ModeloMotocicleta.valueOf(modelo));
            case VAN -> new Van(id, marca, estado, categoria, valorCompra, placa, ano,
                    ModeloVan.valueOf(modelo));
        };

        if (estado == Estado.LOCADO) {
            // Quando o veículo está locado precisamos reconstruir o objeto Locacao para a tela de devolução.
            int dias = rs.getInt("dias");
            double valorTotal = rs.getDouble("valor_total");
            java.sql.Date dataLocacao = rs.getDate("data_locacao");
            int idCliente = rs.getInt("id_cliente");
            Cliente cliente = clienteDAO.buscarPorId(idCliente);
            Calendar data = Calendar.getInstance();
            data.setTime(dataLocacao);
            Locacao locacao = new Locacao(rs.getInt("id_locacao"), dias, valorTotal, data, cliente);
            veiculo.atribuirLocacaoExistente(locacao);
        }
        return veiculo;
    }
}
