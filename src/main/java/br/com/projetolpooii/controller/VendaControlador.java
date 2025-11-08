package br.com.projetolpooii.controller;

import br.com.projetolpooii.dao.HistoricoVendaDAO;
import br.com.projetolpooii.dao.VeiculoDAO;
import br.com.projetolpooii.model.Veiculo;
import br.com.projetolpooii.model.enums.Estado;

import java.sql.SQLException;
import java.util.Calendar;

public class VendaControlador {

    private final VeiculoDAO veiculoDAO;
    private final HistoricoVendaDAO historicoVendaDAO;

    public VendaControlador(VeiculoDAO veiculoDAO, HistoricoVendaDAO historicoVendaDAO) {
        this.veiculoDAO = veiculoDAO;
        this.historicoVendaDAO = historicoVendaDAO;
    }

    public double venderVeiculo(Veiculo veiculo) throws SQLException {
        if (veiculo.getEstado() != Estado.DISPONIVEL && veiculo.getEstado() != Estado.NOVO) {
            throw new IllegalStateException("Somente veículos disponíveis podem ser vendidos.");
        }
        // O valor calculado usa a regra pedida no enunciado.
        double valorVenda = veiculo.getValorParaVenda();
        veiculo.vender();
        veiculoDAO.atualizarEstado(veiculo.getId(), Estado.VENDIDO);
        historicoVendaDAO.registrarVenda(veiculo.getId(), valorVenda, Calendar.getInstance());
        return valorVenda;
    }
}
