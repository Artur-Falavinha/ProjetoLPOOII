package br.com.projetolpooii.controller;

import br.com.projetolpooii.dao.ClienteDAO;
import br.com.projetolpooii.dao.VeiculoDAO;
import br.com.projetolpooii.model.Automovel;
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

import java.sql.SQLException;
import java.util.List;

public class VeiculoControlador {

    private final VeiculoDAO veiculoDAO;
    private final ClienteDAO clienteDAO;

    public VeiculoControlador(VeiculoDAO veiculoDAO, ClienteDAO clienteDAO) {
        this.veiculoDAO = veiculoDAO;
        this.clienteDAO = clienteDAO;
    }

    public Veiculo cadastrarAutomovel(Marca marca,
                                      Estado estado,
                                      Categoria categoria,
                                      double valorCompra,
                                      String placa,
                                      int ano,
                                      ModeloAutomovel modelo) throws SQLException {
        validarPlacaDisponivel(placa);
        Automovel automovel = new Automovel(marca, estado, categoria, valorCompra, placa, ano, modelo);
        return veiculoDAO.inserir(automovel, TipoVeiculo.AUTOMOVEL, modelo.name());
    }

    public Veiculo cadastrarMotocicleta(Marca marca,
                                        Estado estado,
                                        Categoria categoria,
                                        double valorCompra,
                                        String placa,
                                        int ano,
                                        ModeloMotocicleta modelo) throws SQLException {
        validarPlacaDisponivel(placa);
        Motocicleta moto = new Motocicleta(marca, estado, categoria, valorCompra, placa, ano, modelo);
        return veiculoDAO.inserir(moto, TipoVeiculo.MOTOCICLETA, modelo.name());
    }

    public Veiculo cadastrarVan(Marca marca,
                                Estado estado,
                                Categoria categoria,
                                double valorCompra,
                                String placa,
                                int ano,
                                ModeloVan modelo) throws SQLException {
        validarPlacaDisponivel(placa);
        Van van = new Van(marca, estado, categoria, valorCompra, placa, ano, modelo);
        return veiculoDAO.inserir(van, TipoVeiculo.VAN, modelo.name());
    }

    public List<Veiculo> listarDisponiveisParaLocacao(String marcaFiltro,
                                                       Categoria categoria,
                                                       TipoVeiculo tipo) throws SQLException {
        return veiculoDAO.listarDisponiveisParaLocacao(marcaFiltro, categoria, tipo, clienteDAO);
    }

    public List<Veiculo> listarLocados() throws SQLException {
        return veiculoDAO.listarLocados(clienteDAO);
    }

    public List<Veiculo> listarDisponiveisParaVenda(String marcaFiltro,
                                                    Categoria categoria,
                                                    TipoVeiculo tipo) throws SQLException {
        return veiculoDAO.listarDisponiveisParaVenda(marcaFiltro, categoria, tipo, clienteDAO);
    }

    public Veiculo buscarPorId(int idVeiculo) throws SQLException {
        return veiculoDAO.buscarPorId(idVeiculo, clienteDAO);
    }

    public void atualizarEstado(int idVeiculo, Estado estado) throws SQLException {
        veiculoDAO.atualizarEstado(idVeiculo, estado);
    }

    private void validarPlacaDisponivel(String placa) throws SQLException {
        if (veiculoDAO.placaExiste(placa)) {
            throw new IllegalStateException("Já existe um veículo cadastrado com a placa informada.");
        }
    }
}
