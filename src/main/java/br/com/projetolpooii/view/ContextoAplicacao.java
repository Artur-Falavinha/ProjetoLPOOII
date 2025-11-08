package br.com.projetolpooii.view;

import br.com.projetolpooii.controller.ClienteControlador;
import br.com.projetolpooii.controller.LocacaoControlador;
import br.com.projetolpooii.controller.VeiculoControlador;
import br.com.projetolpooii.controller.VendaControlador;
import br.com.projetolpooii.dao.ClienteDAO;
import br.com.projetolpooii.dao.HistoricoVendaDAO;
import br.com.projetolpooii.dao.LocacaoDAO;
import br.com.projetolpooii.dao.VeiculoDAO;

public class ContextoAplicacao {

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();
    private final LocacaoDAO locacaoDAO = new LocacaoDAO();
    private final HistoricoVendaDAO historicoVendaDAO = new HistoricoVendaDAO();

    private final ClienteControlador clienteControlador = new ClienteControlador(clienteDAO);
    private final VeiculoControlador veiculoControlador = new VeiculoControlador(veiculoDAO, clienteDAO);
    private final LocacaoControlador locacaoControlador = new LocacaoControlador(veiculoDAO, locacaoDAO);
    private final VendaControlador vendaControlador = new VendaControlador(veiculoDAO, historicoVendaDAO);

    public ClienteControlador getClienteControlador() {
        return clienteControlador;
    }

    public VeiculoControlador getVeiculoControlador() {
        return veiculoControlador;
    }

    public LocacaoControlador getLocacaoControlador() {
        return locacaoControlador;
    }

    public VendaControlador getVendaControlador() {
        return vendaControlador;
    }
}
