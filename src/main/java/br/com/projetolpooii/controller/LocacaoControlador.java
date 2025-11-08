package br.com.projetolpooii.controller;

import br.com.projetolpooii.dao.LocacaoDAO;
import br.com.projetolpooii.dao.VeiculoDAO;
import br.com.projetolpooii.model.Cliente;
import br.com.projetolpooii.model.Locacao;
import br.com.projetolpooii.model.Veiculo;
import br.com.projetolpooii.model.enums.Estado;

import java.sql.SQLException;
import java.util.Calendar;

public class LocacaoControlador {

    private final VeiculoDAO veiculoDAO;
    private final LocacaoDAO locacaoDAO;

    public LocacaoControlador(VeiculoDAO veiculoDAO, LocacaoDAO locacaoDAO) {
        this.veiculoDAO = veiculoDAO;
        this.locacaoDAO = locacaoDAO;
    }

    public Locacao locarVeiculo(Veiculo veiculo, Cliente cliente, int dias, Calendar data) throws SQLException {
        if (cliente.getId() == null) {
            throw new IllegalArgumentException("Cliente precisa estar salvo para realizar locação.");
        }
        // O próprio objeto veículo aplica regras de negócio ao mudar o estado para LOCADO.
        veiculo.locar(dias, data, cliente);
        Locacao locacao = veiculo.getLocacao();
        // Primeiro registramos a locação para respeitar a restrição de veículo único.
        locacaoDAO.registrar(veiculo.getId(), locacao, cliente.getId());
        veiculoDAO.atualizarEstado(veiculo.getId(), Estado.LOCADO);
        return locacao;
    }

    public void devolverVeiculo(Veiculo veiculo) throws SQLException {
        if (veiculo.getLocacao() == null) {
            throw new IllegalStateException("Veículo não possui locação ativa.");
        }
        // Removemos o registro antes de mudar o estado para evitar registros órfãos.
        locacaoDAO.removerPorVeiculo(veiculo.getId());
        veiculo.devolver();
        veiculoDAO.atualizarEstado(veiculo.getId(), Estado.DISPONIVEL);
    }
}
