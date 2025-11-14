package br.com.projetolpooii.controller;

// Controlador que gerencia as operações com veículos
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

    // DAOs pra acessar os dados no banco
    private final VeiculoDAO veiculoDAO;
    private final ClienteDAO clienteDAO;

    public VeiculoControlador(VeiculoDAO veiculoDAO, ClienteDAO clienteDAO) {
        this.veiculoDAO = veiculoDAO;
        this.clienteDAO = clienteDAO;
    }

    // Cadastra um automóvel novo no sistema
    public Veiculo cadastrarAutomovel(Marca marca,
                                      Estado estado,
                                      Categoria categoria,
                                      double valorCompra,
                                      String placa,
                                      int ano,
                                      ModeloAutomovel modelo) throws SQLException {
        // Verifica se a placa já não tá cadastrada
        validarPlacaDisponivel(placa);
        Automovel automovel = new Automovel(marca, estado, categoria, valorCompra, placa, ano, modelo);
        return veiculoDAO.inserir(automovel, TipoVeiculo.AUTOMOVEL, modelo.name());
    }

    // Cadastra uma moto nova
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

    // Cadastra uma van nova
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

    // Busca veículos que estão disponíveis pra locar, pode filtrar por marca, categoria e tipo
    public List<Veiculo> listarDisponiveisParaLocacao(String marcaFiltro,
                                                       Categoria categoria,
                                                       TipoVeiculo tipo) throws SQLException {
        return veiculoDAO.listarDisponiveisParaLocacao(marcaFiltro, categoria, tipo, clienteDAO);
    }

    // Pega todos os veículos que estão locados no momento
    public List<Veiculo> listarLocados() throws SQLException {
        return veiculoDAO.listarLocados(clienteDAO);
    }

    // Lista veículos disponíveis pra venda com filtros opcionais
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

    // Valida se a placa já não existe no banco antes de cadastrar
    private void validarPlacaDisponivel(String placa) throws SQLException {
        if (veiculoDAO.placaExiste(placa)) {
            throw new IllegalStateException("Já existe um veículo cadastrado com a placa informada.");
        }
    }
}
