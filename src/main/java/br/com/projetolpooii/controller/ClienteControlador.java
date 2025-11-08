package br.com.projetolpooii.controller;

import br.com.projetolpooii.dao.ClienteDAO;
import br.com.projetolpooii.model.Cliente;

import java.sql.SQLException;
import java.util.List;

public class ClienteControlador {

    private final ClienteDAO clienteDAO;

    public ClienteControlador(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public Cliente salvar(Cliente cliente) throws SQLException {
        if (cliente.getId() == null) {
            return clienteDAO.inserir(cliente);
        }
        clienteDAO.atualizar(cliente);
        return cliente;
    }

    public void excluir(Cliente cliente) throws SQLException {
        if (clienteDAO.possuiLocacaoAtiva(cliente.getId())) {
            throw new IllegalStateException("O cliente possui locação ativa e não pode ser excluído.");
        }
        clienteDAO.excluir(cliente.getId());
    }

    public List<Cliente> listarTodos() throws SQLException {
        return clienteDAO.listarTodos();
    }

    public List<Cliente> buscarPorTermo(String termo) throws SQLException {
        return clienteDAO.buscarPorNomeOuDocumento(termo);
    }
}
