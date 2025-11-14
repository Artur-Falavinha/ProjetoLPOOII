package br.com.projetolpooii.controller;

// Controlador que gerencia as locações e devoluções
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

    // Faz a locação de um veículo pra um cliente
    public Locacao locarVeiculo(Veiculo veiculo, Cliente cliente, int dias, Calendar data) throws SQLException {
        if (cliente.getId() == null) {
            throw new IllegalArgumentException("Cliente precisa estar salvo para realizar locação.");
        }
        // Chama o método locar do veículo que já faz as validações
        veiculo.locar(dias, data, cliente);
        Locacao locacao = veiculo.getLocacao();
        // Salva a locação no banco e atualiza o estado do veículo
        locacaoDAO.registrar(veiculo.getId(), locacao, cliente.getId());
        veiculoDAO.atualizarEstado(veiculo.getId(), Estado.LOCADO);
        return locacao;
    }

    // Processa a devolução de um veículo
    public void devolverVeiculo(Veiculo veiculo) throws SQLException {
        if (veiculo.getLocacao() == null) {
            throw new IllegalStateException("Veículo não possui locação ativa.");
        }
        // Remove a locação do banco e volta o veículo pro estado disponível
        locacaoDAO.removerPorVeiculo(veiculo.getId());
        veiculo.devolver();
        veiculoDAO.atualizarEstado(veiculo.getId(), Estado.DISPONIVEL);
    }
}
